package com.khalekuzzaman.just.cse.datacollect.navigaion.screens.home


import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.datacollect.R
import com.khalekuzzaman.just.cse.datacollect.chat_ui.CustomSnackBar
import com.khalekuzzaman.just.cse.datacollect.chat_ui.MessageInputField
import com.khalekuzzaman.just.cse.datacollect.chat_ui.SnackBarMessage
import com.khalekuzzaman.just.cse.datacollect.chat_ui.SnackBarMessageType
import com.khalekuzzaman.just.cse.datacollect.connectivity.ConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.connectivity.NetworkConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.uploadImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MediaUploader(private val context: Context, private val scope: CoroutineScope) {
    private val connectivityObserver = NetworkConnectivityObserver(context).observe()
    private var hasInternet: Boolean = false
    private val imageApiUrl = "http://192.168.10.154:8080/api/images/upload"
    private val videoApiURL = "http://192.168.10.154:8080/api/videos/upload"
    private val _progress = MutableStateFlow(0f)
    val progress = _progress.asStateFlow()
    private val _isUploading = MutableStateFlow(false)
    val isUploading = _isUploading.asStateFlow()
    private val _snackBarMessage = MutableStateFlow<SnackBarMessage?>(null)
    val snackBarMessage = _snackBarMessage.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            connectivityObserver.collect {
                hasInternet = it == ConnectivityObserver.Status.Available
            }

        }
    }

    suspend fun uploadImages(images: List<Uri>) {
        if (!hasInternet) {
            SnackBarMessage("No Internet connection", SnackBarMessageType.Error).update()
            return
        }
        staratLoading()
        images.forEachIndexed { index, uri ->
            val res = uploadImage(
                context = context,
                uri = uri,
                url = imageApiUrl
            )
            _progress.value = (index + 1) / images.size.toFloat()
            if (res.isFailure) {
                stopLoading()
                val message = res.exceptionOrNull()?.message
                SnackBarMessage("$message", SnackBarMessageType.Error).update()
            } else {
                SnackBarMessage("$res", SnackBarMessageType.Success).update()
            }
        }
        stopLoading()

    }

    private fun stopLoading() {
        _isUploading.value = false
    }
    private fun staratLoading() {
        _isUploading.value = true
    }

    private suspend fun SnackBarMessage?.update() {
        _snackBarMessage.value = this
        delay(1000)
        _snackBarMessage.value = null
    }

    fun uploadVideo(videos: List<Uri>) {
        scope.launch {
            _isUploading.value = true
            videos.forEachIndexed { index, uri ->
                val res = uploadImage(context = context, uri = uri, url = videoApiURL)
                _progress.value = (index + 1) / videos.size.toFloat()
                if (res.isFailure) {
                    val message = res.exceptionOrNull()?.message
                    SnackBarMessage("$message", SnackBarMessageType.Error).update()
                } else {
                    SnackBarMessage("$res", SnackBarMessageType.Success).update()
                }
            }
            _isUploading.value = false
        }
    }
}

@Composable
fun HomeScreen(
    images: List<Uri>,
    videos: List<Uri> = emptyList(),
    onVideoPickRequest: () -> Unit,
    onImagePickRequest: () -> Unit = {},
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val mediaUploader = remember { MediaUploader(context, scope) }

    val isUploading = mediaUploader.isUploading.collectAsState().value
    val progress = mediaUploader.progress.collectAsState().value
    val snackBarMessage = mediaUploader.snackBarMessage.collectAsState().value



    Box {
        HomeNonUpLoading(
            pickedImageCount = images.size,
            pickedVideoCount = videos.size,
            onVideoPickRequest = onVideoPickRequest,
            onImagePickRequest = onImagePickRequest,
            snackBarMessage = snackBarMessage,
            onSend = {
                scope.launch {
                    mediaUploader.uploadImages(images = images)
                    //upLoadVideos()
                }

            }
        )
        if (isUploading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Gray.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                LinearProgressIndicator(
                    progress = {
                        progress
                    })
            }
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNonUpLoading(
    snackBarMessage: SnackBarMessage?,
    pickedImageCount: Int = 0,
    pickedVideoCount: Int = 0,
    onVideoPickRequest: () -> Unit,
    onImagePickRequest: () -> Unit = {},
    onSend: () -> Unit = {},
) {

    Scaffold(
        snackbarHost = {
            if (snackBarMessage != null) {
                CustomSnackBar(snackBarMessage)
            }
        },
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    BadgedBox(badge = {
                        if (pickedImageCount > 0) {
                            Badge {
                                Text(text = "$pickedImageCount")
                            }
                        }


                    }) {
                        IconButton(onClick = onImagePickRequest) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_photo),
                                contentDescription = "add photo",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    BadgedBox(badge = {
                        if (pickedVideoCount > 0)
                            Badge {
                                Text(text = "$pickedVideoCount")
                            }
                    }) {

                        IconButton(onClick = onVideoPickRequest) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_video),
                                contentDescription = "add video",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(30.dp)

                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(
                        onClick = onSend,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }

                }

            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WelcomeToHome()
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                MessageInputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                )

            }

        }
    }

}