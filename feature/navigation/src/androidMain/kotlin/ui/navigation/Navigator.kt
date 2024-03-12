package ui.navigation

import androidx.navigation.NavHostController
import navigation.MediaPickerNavGraph
import routes.Destination

class Navigator(
    private val navController: NavHostController
){
    fun navigate(destination: Destination){
        try {
            navController.popBackStack()
            when (destination) {
                Destination.IMAGE_PICKER -> {
                    MediaPickerNavGraph.navigateToImagePicker(navController)
                }
                Destination.VIDEO_PICKER -> {
                    MediaPickerNavGraph.navigateToVideoPicker(navController)
                }
                else -> {
                    navController.navigate(destination.toString())
                }
            }

        } catch (_: Exception) {

        }

    }
    fun pop(){
        try {
            navController.popBackStack()

        } catch (_: Exception) {

        }

    }

}