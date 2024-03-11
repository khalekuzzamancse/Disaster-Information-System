package video_picker

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import image_picker.GalleryScreen
import image_picker.common.KMPFile
import image_picker.common.GalleryMediaGeneric
import image_picker.common.GalleryViewModel


@Composable
fun VideoGalleryGen(
    videoGalleryViewModel: GalleryViewModel,
) {
    var enableAddButton by remember {
        mutableStateOf(true)
    }
    LocalContext.current
    val hasImages = videoGalleryViewModel.galleryState.collectAsState().value.isNotEmpty()
    val videos = videoGalleryViewModel.galleryState.collectAsState().value
    val showRemoveButton = videoGalleryViewModel.anySelected.collectAsState(false).value
    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = {uris->
            videoGalleryViewModel.addImages(uris.map { KMPFile(it.toString()) })
            enableAddButton=true
        }
    )
    GalleryScreen(
        enableAddButton = enableAddButton,
        enabledUndo = videoGalleryViewModel.isUndoAvailable.collectAsState(false).value,
        enabledRedo = videoGalleryViewModel.isUndoAvailable.collectAsState(false).value,
        showRemoveButton = showRemoveButton,
        onAddRequest = {
            enableAddButton=false
            videoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
            )

        },
        onRemoveRequest = videoGalleryViewModel::remove,
        undoRequest = videoGalleryViewModel::undo,
        redoRequest = videoGalleryViewModel::redo
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            if (hasImages) {
                VideoGalleryGen(
                    videos = videos,
                    onSelection = videoGalleryViewModel::flipSelection
                )
            }
            else{
                NoVodeosScreen()
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

@Composable
private fun NoVodeosScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.VideoLibrary,
                contentDescription = "No Images",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 8.dp).size(64.dp)
            )
            Text(
                text = "No Video found",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
            )
            Text(
                text = "Try uploading or capturing some videos.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


