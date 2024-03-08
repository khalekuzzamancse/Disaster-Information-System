package feature.home

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
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
    homeViewModel.progress.collectAsState().value
    val snackBarMessage = homeViewModel.snackBarMessage.collectAsState().value

        HomeCommon(
            pickedImageCount = images.size,
            pickedVideoCount = videos.size,
            onVideoPickRequest = onVideoPickRequest,
            onImagePickRequest = onImagePickRequest,
            snackBarMessage = snackBarMessage,
            isSending = isUploading,
            onSend = {
                scope.launch {
                    homeViewModel.uploadImages(images = images)
                    //homeViewModel.uploadVideo(videos = videos)
                }

            }
        )

}