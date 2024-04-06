package media_picker.ui.navigation


import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.VerticalDivider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import media_picker.ui.pickers.MediaPickersController

import media_picker.ui.pickers.PhotoPickerAndroid
import media_picker.ui.pickers.VideoGalleryAndroid
import ui.navigation.Destination

/**
 * These are top level destination that is why we do not need separate nav host
 * This graph will be used with the drawer or the nav rails
 *
 */

object MediaPickerNavGraph {
    private const val ROUTE = "MediaPickerNavGraph"
    fun navigateToImagePicker(navController: NavHostController) {
        navigateAsTopMostDestination(Destination.ImagePicker, navController)
    }

    fun navigateToVideoPicker(navController: NavHostController) {
        navigateAsTopMostDestination(Destination.VideoPicker, navController)
    }
    fun navigateToMediaPicker(navController: NavHostController) {
        navigateAsTopMostDestination(Destination.MediaPicker, navController)
    }

    private fun navigateAsTopMostDestination(
        destination: Destination,
        navController: NavHostController
    ) {
        navController.navigate(destination.toString()) {
            try {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            } catch (_: Exception) {

            }
        }
    }

    fun graph(
        navGraphBuilder: NavGraphBuilder,
        controller: MediaPickersController,
    ) {
        with(navGraphBuilder) {
            navigation(
                route = ROUTE,
                startDestination = Destination.ImagePicker.toString()
            ) {
                composable(
                    route = Destination.ImagePicker.toString(),
                    enterTransition = { slideInVertically { 1000 } + fadeIn() },
                    exitTransition = { slideOutHorizontally() + fadeOut() }

                ) {
                    PhotoPickerAndroid(
                        viewModel = controller.imageGalleryController
                    )

                }
                composable(
                    route = Destination.VideoPicker.toString(),
                    enterTransition = { slideInVertically { 1000 } + fadeIn() },
                    exitTransition = { slideOutHorizontally() + fadeOut() }
                ) {
                    VideoGalleryAndroid(
                        viewModel = controller.videoGalleryController
                    )

                }
                /**
                 * On Expanded window the image and video picker will be in the same screen
                 */
                composable(
                    route = Destination.MediaPicker.toString(),
                    enterTransition = { slideInVertically { 1000 } + fadeIn() },
                    exitTransition = { slideOutHorizontally() + fadeOut() }
                ) {
                    Row(Modifier.fillMaxWidth()) {
                        Box(Modifier.weight(1f)) {
                            PhotoPickerAndroid(
                                viewModel = controller.imageGalleryController
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        VerticalDivider(Modifier.fillMaxHeight())
                        Spacer(Modifier.width(8.dp))
                        Box(Modifier.weight(1f)) {
                            VideoGalleryAndroid(
                                viewModel = controller.videoGalleryController
                            )
                        }

                    }


                }

            }
        }
    }

}
