package platform_contract

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import components.NavLayoutDecorator
import data_submission.ui.form.ReportFormControllerImpl
import image_picker.common.GalleryViewModel
import navigation.MediaPickerNavGraph
import routes.Destination
import routes.ReportFormRoute
import routes.SplashScreen
import ui.CustomSnackBar
import ui.MainViewModel
import ui.MainViewModelFactory
import ui.navigation.Navigator


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
actual fun NavigationRoot() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val factory = MainViewModelFactory(context)

    val mainViewModel: MainViewModel = viewModel(factory = factory)
    val showSlashScreen = mainViewModel.splashScreenShowing.collectAsState().value

    if (showSlashScreen) {
        SplashScreen()
    } else {
        RootDestination(
            mainViewModel = mainViewModel,
            navController = navController,
        )

    }

}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun RootDestination(
    mainViewModel: MainViewModel,
    navController: NavHostController,
) {

    val selected = mainViewModel.selected.collectAsState().value

    val scope = rememberCoroutineScope()
    val navigator = remember { Navigator(navController) }
    BackHandler {
        // navigator.pop() //causes unwanted behavior fix it
    }

    LaunchedEffect(selected) {
        navigator.navigate(selected)
    }

    NavLayoutDecorator(
        selected = selected,
        onDestinationSelected = mainViewModel::onSelected,
    ) {
        //taking a Box for showing error message
        Box(Modifier.fillMaxSize()) {
            RootNavHost(
                modifier = Modifier,
                navController = navController,
                formController = mainViewModel.reportFormController,
                imageGalleryController = mainViewModel.imageViewModel,
                videoGalleryController = mainViewModel.videoViewModel,
                enableSendButton = mainViewModel.enableSendButton.collectAsState(false).value,
                onSendRequest = mainViewModel::upload,
                onExitRequestFromHomeModule = {}
            )
            val errorMessage = mainViewModel.snackBarMessage.collectAsState().value

            AnimatedVisibility(
                visible = errorMessage != null,
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                errorMessage?.let { message ->
                    CustomSnackBar(message)
                }

            }
        }

    }


}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun RootNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    formController: ReportFormControllerImpl,
    imageGalleryController: GalleryViewModel,
    videoGalleryController: GalleryViewModel,
    enableSendButton: Boolean,
    onSendRequest: () -> Unit,
    onExitRequestFromHomeModule: () -> Unit,
) {


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destination.HOME.toString()
    ) {
        composable(
            route = Destination.HOME.toString(),
            enterTransition = { slideInVertically { 1000 } + fadeIn() },
            exitTransition = { slideOutHorizontally() + fadeOut() }
        ) {
            HomeModuleNavGraph(
                enableSendButton = enableSendButton,
                onSendRequest = onSendRequest,
                onExitRequest = onExitRequestFromHomeModule
            )
        }
        MediaPickerNavGraph.graph(
            navGraphBuilder = this,
            imageGalleryViewModel = imageGalleryController,
            videoGalleryViewModel = videoGalleryController
        )
        composable(
            route = Destination.REPORT_FORM.toString(),
            enterTransition = { slideInHorizontally() + fadeIn() },
            exitTransition = { slideOutHorizontally() + fadeOut() }
        ) {
            ReportFormRoute(formController)
        }


    }


}