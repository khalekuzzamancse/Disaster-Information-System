package routes

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


/**
 * It just provide the nav layout decorator
 */
@Composable
fun NavLayoutDecorator(
    onDestinationSelected: (Destination) -> Unit,
    selected: Destination,
    content: @Composable () -> Unit,
) {
    val items = listOf(
        BottomToNavDecoratorItem("Home", Icons.Default.Home),
        BottomToNavDecoratorItem("Form", Icons.Default.NoteAlt),
        BottomToNavDecoratorItem("Image Picker", Icons.Default.Image),
        BottomToNavDecoratorItem("Video Picker", Icons.Default.VideoFile),
    )
    BottomBarToNavRailDecorator(
        destinations = items,
        onItemSelected = { index ->
            when (index) {
                0->onDestinationSelected(Destination.HOME)
                1->onDestinationSelected(Destination.REPORT_FORM)
                2->onDestinationSelected(Destination.IMAGE_PICKER)
                3->onDestinationSelected(Destination.VIDEO_PICKER)
            }
        },
        selected = selected.order
    ) {
        content()

    }

}