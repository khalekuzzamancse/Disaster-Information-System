package components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PermMedia
import androidx.compose.material.icons.filled.TableView
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PermMedia
import androidx.compose.material.icons.outlined.TableView
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import routes.Destination


/**
 * It just provide the nav layout decorator
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun NavLayoutDecorator(
    modifier: Modifier,
    onDestinationSelected: (Destination) -> Unit,
    selected: Destination,
    content: @Composable () -> Unit,
) {
    val windowSize = calculateWindowSizeClass().widthSizeClass
    val expanded = WindowWidthSizeClass.Expanded


    val items = if (windowSize==expanded) createDestinationForExpandedWindow() else createNonExpandedDestination()

    LaunchedEffect(selected){
        //if selected destination is MediaPicker,but the window size changed to non expanded
        //in that case the media picker is no longer available,so by default select the image picker
        // and the vice versa
        if (selected==Destination.MediaPicker&&windowSize!=expanded){
            onDestinationSelected(Destination.IMAGE_PICKER)
        }
        if (selected==Destination.IMAGE_PICKER&&windowSize==expanded){
            onDestinationSelected(Destination.MediaPicker)
        }
    }

    BottomBarToNavRailDecorator(
        modifier = modifier,
        destinations = items,
        onDestinationSelected = onDestinationSelected,
        selected = selected.order
    ) {
        content()

    }

}


private fun createNonExpandedDestination(): List<NavigationItem> {
    return listOf(
        NavigationItem(
            label = "Home",
            focusedIcon = Icons.Filled.Home,
            unFocusedIcon = Icons.Outlined.Home,
            destination = Destination.HOME
        ),
        NavigationItem(
            label = "Form",
            focusedIcon = Icons.Default.TableView,
            unFocusedIcon = Icons.Outlined.TableView,
            destination = Destination.REPORT_FORM
        ),
        NavigationItem(
            label = "Add Images",
            focusedIcon = Icons.Default.AddAPhoto,
            unFocusedIcon = Icons.Outlined.AddAPhoto,
            destination = Destination.IMAGE_PICKER
        ),
        NavigationItem(
            label = "Add Videos",
            focusedIcon = Icons.Default.VideoLibrary,
            unFocusedIcon = Icons.Outlined.VideoLibrary,
            destination = Destination.VIDEO_PICKER
        ),
    )
}

private fun createDestinationForExpandedWindow(): List<NavigationItem> {
    return listOf(
        NavigationItem(
            label = "Home",
            focusedIcon = Icons.Filled.Home,
            unFocusedIcon = Icons.Outlined.Home,
            destination = Destination.HOME
        ),
        NavigationItem(
            label = "Form",
            focusedIcon = Icons.Default.TableView,
            unFocusedIcon = Icons.Outlined.TableView,
            destination = Destination.REPORT_FORM
        ),
        NavigationItem(
            label = "Add Media",
            focusedIcon = Icons.Default.PermMedia,
            unFocusedIcon = Icons.Outlined.PermMedia,
            destination = Destination.MediaPicker
        ),
    )
}