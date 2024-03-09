package image_picker
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Stateless Reusable gallery preview screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    enableAddButton: Boolean,
    enabledUndo: Boolean,
    enabledRedo: Boolean,
    showRemoveButton: Boolean,
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
                        enabled =enabledUndo,
                        onClick = undoRequest
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    RedoButton(
                        enabled =enabledRedo,
                        onClick = redoRequest
                    )
                }
            )
        },
        floatingActionButton = {
            if (showRemoveButton) {
                RemoveButton(
                    onClick=onRemoveRequest
                )
            } else {
                AddButton(
                    onClick =onAddRequest,
                    enabled = enableAddButton

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

    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Undo,
            contentDescription = "Add Image",
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
            contentDescription = "Add Image",
        )
    }

}

@Composable
private fun AddButton(
    onClick: () -> Unit,
    enabled: Boolean,
) {
    IconButton(
        colors = IconButtonDefaults.iconButtonColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
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
            contentDescription = "Add Image",
        )
    }

}
