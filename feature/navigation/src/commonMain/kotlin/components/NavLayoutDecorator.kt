package components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.TableView
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.TableView
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import routes.Destination


/**
 * It just provide the nav layout decorator
 */
@Composable
fun NavLayoutDecorator(
    modifier: Modifier=Modifier,
    onDestinationSelected: (Destination) -> Unit,
    selected: Destination,
    content: @Composable () -> Unit,
) {
    val items = createBottomDestination()
    BottomBarToNavRailDecorator(
        modifier=modifier,
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
private fun createBottomDestination():List<BottomToNavDecoratorItem>{
   return listOf(
        BottomToNavDecoratorItem(label = "Home", focusedIcon = Icons.Filled.Home, unFocusedIcon = Icons.Outlined.Home ),
        BottomToNavDecoratorItem(label = "Form", focusedIcon = Icons.Default.TableView,unFocusedIcon = Icons.Outlined.TableView ),
        BottomToNavDecoratorItem(label = "Add Images", focusedIcon = Icons.Default.AddAPhoto,unFocusedIcon = Icons.Outlined.AddAPhoto ),
        BottomToNavDecoratorItem(label = "Add Videos", focusedIcon = Icons.Default.VideoLibrary,unFocusedIcon = Icons.Outlined.VideoLibrary ),
    )
}