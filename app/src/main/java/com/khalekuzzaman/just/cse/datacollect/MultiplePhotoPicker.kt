package com.khalekuzzaman.just.cse.datacollect

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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.khalekuzzaman.just.cse.datacollect.common_ui.GalleryScreen
import com.khalekuzzaman.just.cse.datacollect.common_ui.command_pattern.Command
import com.khalekuzzaman.just.cse.datacollect.common_ui.command_pattern.ImageGalleryCommands
import com.khalekuzzaman.just.cse.datacollect.common_ui.command_pattern.UndoManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

@Immutable
data class GalleryImage(
    val uri: Uri,
    val isSelected: Boolean = false
)

@Immutable
data class ImageGalleryState(
    val images: List<GalleryImage> = emptyList()
)

class ViewModel {
    private val _imageGalleryState = MutableStateFlow(ImageGalleryState(emptyList()))
    val imageGalleryState = _imageGalleryState.asStateFlow()
    val anySelected: Flow<Boolean> = _imageGalleryState.map { state ->
        state.images.any { it.isSelected }
    }
    private var lastCommand: Command? = null

    fun addImages(uris: List<Uri>) {
        val command = ImageGalleryCommands.Add(
            images = uris.map { GalleryImage(uri = it) },
            currentState = _imageGalleryState.value
        )
        undoManager.execute(command)
        command.execute()
        lastCommand = command
        updateState(command.state)
    }

    fun remove() {
        val command = ImageGalleryCommands.Remove(_imageGalleryState.value)
        command.execute()
        lastCommand = command
        updateState(command.state)
    }

    fun undo() {
        lastCommand?.let { command ->
            command.undo()
            when (command) {
                is ImageGalleryCommands.Add -> {
                    updateState(command.state)
                }
                is ImageGalleryCommands.Remove->{
                    updateState(command.state)
                }
            }

        }

    }

    fun redo() {
        lastCommand?.let { command ->
            command.redo()
            when (command) {
                is ImageGalleryCommands.Add -> {
                    updateState(command.state)
                }
                is ImageGalleryCommands.Remove->{
                    updateState(command.state)
                }
            }

        }

    }

    private fun updateState(imageGalleryState: ImageGalleryState) {
        _imageGalleryState.update { imageGalleryState }
    }

    fun flipSelection(currentSelected: Uri) {
        val command = ImageGalleryCommands.FlipSelection(
            currentState = _imageGalleryState.value,
            uri = currentSelected
        )
        command.execute()
        updateState(command.state)
    }


}

val viewModel = ViewModel()
val undoManager = UndoManager()

@Composable
fun MultiplePhotoPicker() {
    LocalContext.current
    val showImageGallery = viewModel.imageGalleryState.collectAsState().value.images.isNotEmpty()
    val images = viewModel.imageGalleryState.collectAsState().value.images
    val showRemoveButton = viewModel.anySelected.collectAsState(false).value
    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = viewModel::addImages
    )
    GalleryScreen(
        enabledUndo = true,
        enabledRedo = true,
        showRemoveButton = showRemoveButton,
        onExitRequest = { /*TODO*/ },
        onAddRequest = {
            multiplePhotoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        onRemoveRequest = viewModel::remove,
        undoRequest = viewModel::undo,
        redoRequest = viewModel::redo
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            if (showImageGallery) {
                ImageGallery(
                    uiState = ImageGalleryState(images),
                    onSelection = viewModel::flipSelection
                )
            }
        }

    }

}


@Composable
fun ImageGallery(
    uiState: ImageGalleryState,
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
            items = uiState.images,
            itemContent = { image ->
                Box(
                    modifier = Modifier
                        .clickable {
                            viewModel.flipSelection(image.uri)
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

