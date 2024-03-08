package image_picker.video_picker

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import image_picker.GalleryScreenG
import image_picker.common.KMPFile
import image_picker.common.GalleryMediaGeneric
import image_picker.common.GalleryViewModelG


@Composable
fun VideoGalleryGen(
    videoGalleryViewModel: GalleryViewModelG,
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
        onResult = {uris->
            videoGalleryViewModel.addImages(uris.map { KMPFile(it.toString()) })
            enableAddButton=true
        }
    )
    GalleryScreenG(
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
                VideoGalleryGen(
                    videos = videos,
                    onSelection = videoGalleryViewModel::flipSelection
                )
            }
        }

    }


}


@Composable
fun VideoGalleryGen(
    videos: List<GalleryMediaGeneric>,
    onSelection: (KMPFile) -> Unit,
) {

    val state = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        modifier = Modifier,
        columns = StaggeredGridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp,
        state = state,
    ) {
        items(
            items = videos,
            itemContent = { video ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 2.dp,
                            color = if (video.isSelected) Color.Red
                            else MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            onSelection(video.identity)
                        }

                ) {
                    VideoPreviewer(
                        file = video.identity
                    )
                    if (video.isSelected) {
                        Checkbox(
                            checked = true,
                            onCheckedChange = {
                                onSelection(video.identity)
                            },
                            modifier = Modifier.align(Alignment.BottomEnd)
                        )
                    }
                }

            }
        )
    }

}