package com.khalekuzzaman.just.cse.datacollect.ui_layer.navigaion.screens.home


import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.datacollect.R
import com.khalekuzzaman.just.cse.datacollect.ui_layer.chat_ui.CustomSnackBar
import com.khalekuzzaman.just.cse.datacollect.ui_layer.chat_ui.SnackBarMessage
import kotlinx.coroutines.launch
import platform_contracts.DateUtilsCustom
import ui.form.BaseDescriptionFormManager
import ui.routes.SubmitFormRoutes


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
    homeViewModel.progress.collectAsState().value
    val snackBarMessage = homeViewModel.snackBarMessage.collectAsState().value

    Box {
        HomeNonUpLoading(
            pickedImageCount = images.size,
            pickedVideoCount = videos.size,
            onVideoPickRequest = onVideoPickRequest,
            onImagePickRequest = onImagePickRequest,
            snackBarMessage = snackBarMessage,
            isSending = isUploading,
            onSend = {
                scope.launch {
                    // homeViewModel.uploadImages(images = images)
                    homeViewModel.uploadVideo(videos = videos)
                }

            }
        )
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
    isSending: Boolean,
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
                    AddImageButton(pickedImageCount, enable = !isSending, onImagePickRequest)
                    Spacer(modifier = Modifier.width(4.dp))
                    AddVideoButton(pickedVideoCount, enable =! isSending, onVideoPickRequest)
                    Spacer(modifier = Modifier.width(4.dp))
                    SentButton(enable =! isSending, onSend)
                }

            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())

            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WelcomeToHome()
            Spacer(modifier = Modifier.height(16.dp))
            val viewModel = remember { BaseDescriptionFormManager(DateUtilsCustom()) }
            SubmitFormRoutes(formStateManager = viewModel, onSubmitClick = {})
        }
    }

}

@Composable
private fun SentButton(enable: Boolean, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        enabled = enable
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = null,
            tint = if (enable) MaterialTheme.colorScheme.primary else LocalContentColor.current,
        )
    }
}

@Composable
private fun AddImageButton(pickedImageCount: Int, enable: Boolean, onClick: () -> Unit) {
    BadgeDecorator(pickedImageCount) {
        IconButton(
            onClick = onClick,
            enabled = enable
        ) {
            Icon(
                painter = painterResource(id = core.work_manager.R.drawable.add_photo),
                contentDescription = "add video",
                tint = if (enable) MaterialTheme.colorScheme.primary else LocalContentColor.current,
                modifier = Modifier.size(30.dp)

            )
        }
    }

}

@Composable
private fun AddVideoButton(pickedVideoCount: Int, enable: Boolean, onClick: () -> Unit) {
    BadgeDecorator(pickedVideoCount) {
        IconButton(onClick = onClick ,enabled = enable) {
            Icon(
                painter = painterResource(id = R.drawable.add_video),
                contentDescription = "add video",
                tint = if (enable) MaterialTheme.colorScheme.primary else LocalContentColor.current,
                modifier = Modifier.size(30.dp)

            )
        }
    }

}

@Composable
private fun BadgeDecorator(count: Int, content: @Composable () -> Unit) {
    BadgedBox(
        badge = {
            if (count > 0)
                Badge {
                    Text(text = "$count")
                }
        },
    ) {
        content()
    }
}