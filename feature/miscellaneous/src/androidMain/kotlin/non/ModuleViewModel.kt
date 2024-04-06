package non

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import feature.home.ui.destination.Destination

internal class ModuleViewModel(
     val navHostController: NavHostController,
) : ViewModel() {

    fun navigateTo(destination: Destination) {
        try {
            navHostController.navigate(destination.toString())
        } catch (_: Exception) {
        }
    }
    fun isAtHome(): Boolean = navHostController.currentBackStackEntry?.destination?.route == Destination.Home.toString()
    fun popDestination(){
        navHostController.popBackStack()
    }

}