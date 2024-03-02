package com.khalekuzzaman.just.cse.datacollect.navigaion.screens.home


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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.datacollect.R
import com.khalekuzzaman.just.cse.datacollect.chat_ui.MessageInputField
import com.khalekuzzaman.just.cse.datacollect.uploadImage
import com.khalekuzzaman.just.cse.datacollect.uploadVideo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    images: List<Uri>,
    videos: List<Uri> = emptyList(),
    onVideoPickRequest: () -> Unit,
    onImagePickRequest: () -> Unit = {},
) {
    val context = LocalContext.current
    var isUploading by remember {
        mutableStateOf(false)
    }
    var progress by remember {
        mutableFloatStateOf(100f)
    }
    val scope = remember {
        CoroutineScope(Dispatchers.IO)
    }
    val upLoadImages: () -> Unit = {
        scope.launch {
            isUploading = true
            progress = 0f
            images.forEachIndexed { index, uri ->
                val res = uploadImage(
                    context = context,
                    url = "http://192.168.10.154:8080/api/images/upload",
                    uri = uri
                )
                with(Dispatchers.Main){
                    progress = (index + 1f) / images.size
                    println("ImageAPITEST:$res")
                }

            }
            with(Dispatchers.Main){
                isUploading = false
            }

        }
    }
    val upLoadVideos: () -> Unit = {
        scope.launch {
            isUploading = true
            progress = 0f
            videos.forEachIndexed { index, uri ->
                val res = uploadVideo(
                    context = context,
                    url = "http://192.168.10.154:8080/api/videos/upload",
                    uri = uri
                )
                progress = (index*1f) / images.size
                println("VideosAPITEST:$res")
            }
            isUploading = false
        }
    }
    Box {
        HomeNonUpLoading(
            pickedImageCount = images.size,
            pickedVideoCount = videos.size,
            onVideoPickRequest = onVideoPickRequest,
            onImagePickRequest = onImagePickRequest,
            onSend = {
               // upLoadImages()
                upLoadVideos()
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
    pickedImageCount: Int = 0,
    pickedVideoCount: Int = 0,
    onVideoPickRequest: () -> Unit,
    onImagePickRequest: () -> Unit = {},
    onSend: () -> Unit = {},
) {

    Scaffold(
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