package ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import ui.media_picker.GalleryScreen
import ui.media_picker.common.KMPFile
import ui.media_picker.common.GalleryMediaGeneric
import ui.media_picker.common.MediaGalleryController
import ui.permission.PermissionDecorator
import ui.permission.PermissionFactory


/**
 * Only the public API
 */

@Composable
internal fun PhotoPickerAndroid(viewModel: MediaGalleryController) {
    PermissionDecorator(
        permissions = PermissionFactory.storagePermissions()
    ){
        PhotoPicker(viewModel)
    }
}
@Composable
private fun PhotoPicker(
    viewModel: MediaGalleryController,
) {
    var enableAddButton by remember {
        mutableStateOf(true)
    }
    LocalContext.current
    val hasImages =
        viewModel.galleryState.collectAsState().value.isNotEmpty()
    val images = viewModel.galleryState.collectAsState().value
    val showRemoveButton = viewModel.anySelected.collectAsState(false).value
    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = {uris->
            viewModel.addImages(uris.map { KMPFile(it.toString() ) })
            enableAddButton=true
        }
    )

    GalleryScreen(
        enableAddButton = enableAddButton,
        enabledUndo = viewModel.isUndoAvailable.collectAsState(false).value,
        enabledRedo = viewModel.isUndoAvailable.collectAsState(false).value,
        showRemoveButton = showRemoveButton,
        onAddRequest = {
            enableAddButton=false
            multiplePhotoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        addButtonColor = MaterialTheme.colorScheme.tertiary,
        onRemoveRequest = viewModel::remove,
        undoRequest = viewModel::undo,
        redoRequest = viewModel::redo
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            if (hasImages) {
                ImageGallery(
                    images = images,
                    onSelection = viewModel::flipSelection,
                    imageMediaGalleryController = viewModel
                )
            }
            else{
                NoImagesScreen()
            }
        }

    }

}


@Composable
internal fun ImageGallery(
    imageMediaGalleryController: MediaGalleryController,
    images: List<GalleryMediaGeneric>,
    onSelection: (KMPFile) -> Unit,
) {


    val state = rememberLazyStaggeredGridState()

    Box {
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
                                imageMediaGalleryController.flipSelection(image.identity)
                            }
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 2.dp,
                                color = if (image.isSelected) Color.Red
                                else MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Image(
                            identity = image.identity,
                        )
                        if (image.isSelected) {
                            Checkbox(
                                checked = true,
                                onCheckedChange = {
                                    onSelection(image.identity)
                                },
                                modifier = Modifier.align(Alignment.BottomEnd)
                            )
                        }
                    }
                }
            )
        }

    }
}


@Composable
private fun Image(
    identity: KMPFile,
) {

    var isLoading by remember {
        mutableStateOf(false)
    }
    Box {
        AsyncImage(
            model =identity.id.toUri(),//use the androidx.core.net Uri
            contentDescription = null,
            modifier = Modifier,
            onState = {
                isLoading = it is AsyncImagePainter.State.Loading
            },

            )
        if (isLoading) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.7f))) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)

                )
            }
        }


    }


}

@Composable
private fun NoImagesScreen() {
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
                imageVector = Icons.Default.Image,
                contentDescription = "No Images",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(bottom = 8.dp).size(64.dp)

            )
            Text(
                text = "No images found",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
            )
            Text(
                text = "Try uploading or capturing some images.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}



