package navigation


import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import image_picker.PhotoPickerAndroid
import image_picker.common.GalleryViewModel
import video_picker.VideoGalleryAndroid

/**
 * These are top level destination that is why we do not need separate nav host
 * This graph will be used with the drawer or the nav rails
 *
 */

object MediaPickerNavGraph {
    const val ROUTE = "MediaPickerNavGraph"
    fun navigateToImagePicker(navController: NavHostController) {
        navigateAsTopMostDestination(Destination.ImagePicker, navController)
    }

    fun navigateToVideoPicker(navController: NavHostController) {
        navigateAsTopMostDestination(Destination.VideoPicker, navController)
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
        imageGalleryViewModel: GalleryViewModel,
        videoGalleryViewModel: GalleryViewModel,
    ) {
        with(navGraphBuilder) {
            navigation(
                route = ROUTE,
                startDestination = Destination.ImagePicker.toString()
            ) {
                composable(route = Destination.ImagePicker.toString()) {
                    PhotoPickerAndroid(
                        viewModel = imageGalleryViewModel
                    )

                }
                composable(route = Destination.VideoPicker.toString()) {
                    VideoGalleryAndroid(
                        viewModel = videoGalleryViewModel
                    )

                }
            }
        }
    }

}
