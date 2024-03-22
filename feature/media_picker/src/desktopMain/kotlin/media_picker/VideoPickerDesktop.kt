package media_picker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.media_picker.GalleryScreen

@Composable
fun VideoPickerDesktop() {
    GalleryScreen(
        enableAddButton = false,
        enabledUndo = true,
        enabledRedo = true,
        showRemoveButton = true,
        onAddRequest = {

        },
        onRemoveRequest = {  },
        undoRequest = {},
        redoRequest = {},

    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
           Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Videos Here")
        }

    }

}