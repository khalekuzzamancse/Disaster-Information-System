package ui.media_picker

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@PublishedApi
@Composable
internal fun GalleryScreen(
    enableAddButton: Boolean,
    enabledUndo: Boolean,
    enabledRedo: Boolean,
    showRemoveButton: Boolean,
    addButtonColor: Color=MaterialTheme.colorScheme.primary,
    onAddRequest: () -> Unit,
    onRemoveRequest: () -> Unit,
    undoRequest: () -> Unit,
    redoRequest: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                actions = {
                    UndoButton(
                        enabled = enabledUndo,
                        onClick = undoRequest
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    RedoButton(
                        enabled = enabledRedo,
                        onClick = redoRequest
                    )
                }
            )
        },
        floatingActionButton = {
            if (showRemoveButton) {
                RemoveButton(
                    onClick = onRemoveRequest
                )
            } else {
                AddButton(
                    onClick = onAddRequest,
                    enabled = enableAddButton,
                    color = addButtonColor

                )

            }
        }
    ) {
        content(it)
    }

}

@Composable
private fun UndoButton(
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    IconButton(
        enabled = enabled,
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors().copy(
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Undo,
            contentDescription = "Undo Button",
        )
    }

}

@Composable
private fun RedoButton(
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    IconButton(
        enabled = enabled,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Redo,
            contentDescription = "Redo Button",
        )
    }

}

@Composable
private fun AddButton(
    onClick: () -> Unit,
    enabled: Boolean,
    color: Color=MaterialTheme.colorScheme.primary,
) {
    IconButton(
        colors = IconButtonDefaults.iconButtonColors().copy(
            containerColor = color,
            contentColor = MaterialTheme.colorScheme.contentColorFor(color),
        ),
        onClick = onClick,
        modifier = Modifier.size(56.dp),
        enabled = enabled
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Image",
        )
    }

}

@Composable
private fun RemoveButton(
    onClick: () -> Unit
) {
    IconButton(
        colors = IconButtonDefaults.iconButtonColors().copy(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError,
        ),
        onClick = onClick,
        modifier = Modifier.size(56.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Remove Button",
        )
    }

}
