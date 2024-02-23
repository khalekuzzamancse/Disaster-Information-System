package com.khalekuzzaman.just.cse.datacollect.image_picker

import android.net.Uri
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.khalekuzzaman.just.cse.datacollect.common_ui.GalleryScreen


private val imageGalleryViewModel = GalleryViewModel()

@Composable
fun MultiplePhotoPicker(
    onExitRequest:()->Unit,
) {
    LocalContext.current
    val showImageGallery = imageGalleryViewModel.imageGalleryState.collectAsState().value.isNotEmpty()
    val images = imageGalleryViewModel.imageGalleryState.collectAsState().value
    val showRemoveButton = imageGalleryViewModel.anySelected.collectAsState(false).value
    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = imageGalleryViewModel::addImages
    )
    GalleryScreen(
        enabledUndo = true,
        enabledRedo = true,
        showRemoveButton = showRemoveButton,
        onExitRequest = onExitRequest,
        onAddRequest = {
            multiplePhotoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        onRemoveRequest = imageGalleryViewModel::remove,
        undoRequest = imageGalleryViewModel::undo,
        redoRequest = imageGalleryViewModel::redo
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            if (showImageGallery) {
                ImageGallery(
                    images = images,
                    onSelection = imageGalleryViewModel::flipSelection
                )
            }
        }

    }

}


@Composable
fun ImageGallery(
    images: List<GalleryMedia>,
    onSelection: (Uri) -> Unit,
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
            items = images,
            itemContent = { image ->
                Box(
                    modifier = Modifier
                        .clickable {
                            imageGalleryViewModel.flipSelection(image.uri)
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 2.dp,
                            color = if (image.isSelected) Color.Red
                            else MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Image(uri = image.uri)
                    if (image.isSelected) {
                        Checkbox(
                            checked = true,
                            onCheckedChange = {
                                onSelection(image.uri)
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
private fun Image(
    uri: Uri
) {
    AsyncImage(
        model = uri,
        contentDescription = null,
        modifier = Modifier

    )

}

