package com.khalekuzzaman.just.cse.datacollect.module_1.navigaion.screens.home


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
import com.khalekuzzaman.just.cse.datacollect.module_1.chat_ui.CustomSnackBar
import com.khalekuzzaman.just.cse.datacollect.module_1.chat_ui.MessageInputField
import com.khalekuzzaman.just.cse.datacollect.module_1.chat_ui.SnackBarMessage
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    images: List<Uri>,
    videos: List<Uri> = emptyList(),
    onVideoPickRequest: () -> Unit,
    onImagePickRequest: () -> Unit = {},
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val homeViewModel = remember { HomeViewModel(context) }

    val isUploading = homeViewModel.isUploading.collectAsState().value
    val progress = homeViewModel.progress.collectAsState().value
    val snackBarMessage = homeViewModel.snackBarMessage.collectAsState().value



    Box {
        HomeNonUpLoading(
            pickedImageCount = images.size,
            pickedVideoCount = videos.size,
            onVideoPickRequest = onVideoPickRequest,
            onImagePickRequest = onImagePickRequest,
            snackBarMessage = snackBarMessage,
            onSend = {
                scope.launch {
                    homeViewModel.uploadImages(images = images)
                  //  homeViewModel.uploadVideo(videos = videos)
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