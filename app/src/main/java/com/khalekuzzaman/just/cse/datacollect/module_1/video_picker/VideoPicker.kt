package com.khalekuzzaman.just.cse.datacollect.module_1.video_picker


import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.BuildConfig
import com.khalekuzzaman.just.cse.datacollect.module_1.common_ui.GalleryScreen
import com.khalekuzzaman.just.cse.datacollect.module_1.common_ui.VideoPlayerScreen
import com.khalekuzzaman.just.cse.datacollect.module_1.image_picker.GalleryViewModel


@Composable
fun VideoGallery(
    videoGalleryViewModel: GalleryViewModel,
    onExitRequest:()->Unit,
) {
    var enableAddButton by remember {
        mutableStateOf(true)
    }
    LocalContext.current
    val showGallery = videoGalleryViewModel.galleryState.collectAsState().value.isNotEmpty()
    val videos = videoGalleryViewModel.galleryState.collectAsState().value
    val showRemoveButton = videoGalleryViewModel.anySelected.collectAsState(false).value
    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = {
            videoGalleryViewModel.addImages(it)
            enableAddButton=true
        }
    )
    GalleryScreen(
        enabledUndo = true,
        enabledRedo = true,
        showRemoveButton = showRemoveButton,
        onExitRequest =onExitRequest,
        onAddRequest = {
            enableAddButton=false
            videoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
            )

        },
        onRemoveRequest = videoGalleryViewModel::remove,
        undoRequest = videoGalleryViewModel::undo,
        redoRequest = videoGalleryViewModel::redo,
        enableAddButton = enableAddButton
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            if (showGallery) {
                VideoGallery(
                    videos = videos,
                    onSelection = videoGalleryViewModel::flipSelection
                )
            }
        }

    }


}

@Composable
fun SingleVideoPicker() {
    var uri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
        }
    )

    val context = LocalContext.current

    Column(
    ) {
        Button(onClick = {
            singlePhotoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
            )

        }) {
            Text("Pick Video")
        }
        val mediaMetadataRetriever = MediaMetadataRetriever()
        var thumbnail: Bitmap? = null
        try {
            mediaMetadataRetriever.setDataSource(context, uri)
            thumbnail = mediaMetadataRetriever.getFrameAtTime(100) // time in Micros
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        } finally {
            mediaMetadataRetriever.release()
        }

//        AsyncImage(model = thumbnail, contentDescription = null, modifier = Modifier.size(248.dp))

        uri?.let {
            VideoPlayerScreen(uri = it)
        }

    }
}