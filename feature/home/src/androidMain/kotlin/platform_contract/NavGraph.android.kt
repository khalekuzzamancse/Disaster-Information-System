package platform_contract

import non.ModuleViewModel
import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import feature.home.ui.destination.Destination
import feature.home.ui.destination.about_us.AboutUs
import non.HomeDestinationAndroid
import ui.SnackBarMessage


@Composable
actual fun HomeModuleNavGraph(
    enableSendButton: Boolean,
    onSendRequest: () -> Unit,
    onExitRequest: () -> Unit,
) {
    val navController = rememberNavController()
    val viewModel = remember { ModuleViewModel(navController) }
    HomeNavHost(
        navController = viewModel.navHostController,
        onSendRequest = onSendRequest,
        onBackPressed = {
            if (viewModel.isAtHome())
                onExitRequest()
            else
                viewModel.popDestination()
        },
        onNavigationRequest = viewModel::navigateTo,
        enableSend = enableSendButton
    )


}

@Composable
private fun HomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    snackBarMessage: SnackBarMessage? = null,
    enableSend: Boolean,
    onNavigationRequest: (Destination) -> Unit,
    onBackPressed: () -> Unit,
    onSendRequest: () -> Unit,
) {
    BackHandler {
        onBackPressed()
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destination.Home.toString()
    ) {
        composable(
            route = Destination.Home.toString(),
            enterTransition = { slideInVertically { 1000 } + fadeIn() },
            exitTransition = { slideOutHorizontally() + fadeOut() }
        ) {
            HomeDestinationAndroid(
                snackBarMessage = snackBarMessage,
                enableSend = enableSend,
                onSendRequest = onSendRequest,
                onAboutUsRequest = {
                    onNavigationRequest(Destination.AboutUs)
                }
            )
        }
        composable(
            route = Destination.AboutUs.toString(),
            enterTransition = { slideInHorizontally() + fadeIn() },
            exitTransition = { slideOutHorizontally() + fadeOut() }
        ) {
            AboutUs()
        }

    }


}