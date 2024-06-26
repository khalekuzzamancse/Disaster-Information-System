package platform_contract

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import components.NavLayoutDecorator
import media_picker.di.MediaPickerFactory
import report_form.domain.ReportFormController
import report_form.ui.ReportFormNavGraph
import routes.Destination
import routes.SplashScreen
import ui.CustomSnackBar
import media_picker.ui.pickers.MediaPickersController
import ui.navigation.MainViewModel
import ui.navigation.MainViewModelFactory
import media_picker.ui.navigation.MediaPickerNavGraph
import ui.navigation.Navigator


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
actual fun NavigationRoot() {
    LocalContext.current
    val navController = rememberNavController()
    val factory = MainViewModelFactory(MediaPickerFactory.mediaPickerController())
    val mainViewModel: MainViewModel = viewModel(factory = factory)
    val showSlashScreen = mainViewModel.splashScreenShowing.collectAsState().value
    RootDestination(
        mainViewModel = mainViewModel,
        navController = navController,
    )
//    if (showSlashScreen) {
//        SplashScreen()
//    } else {


    //  }

}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun RootDestination(
    mainViewModel: MainViewModel,
    navController: NavHostController,
) {

    val selected = mainViewModel.selected.collectAsState().value

    val navigator = remember { Navigator(navController) }
    BackHandler {
        // navigator.pop() //causes unwanted behavior fix it
    }

    LaunchedEffect(selected) {
        navigator.navigate(selected)
    }
    //to show error message using scaffold
    //Beware of since Scaffold is sub compose Layout so making it   parent scrollable may causes crash
    //Taking as the parent of all UI component so that error message does is top of other ui component
    val errorMessage = mainViewModel.snackBarMessage.collectAsState(null).value

    Scaffold(
        snackbarHost = {
            errorMessage?.let { message ->
                CustomSnackBar(message)
            }
        }
    ) {
        NavLayoutDecorator(
            modifier = Modifier
                .padding(it)
                .semantics (mergeDescendants = true){
                    contentDescription = "Entire  screen"
                },
            selected = selected,
            onDestinationSelected = mainViewModel::onSelected,
        ) {

            RootNavHost(
                modifier = Modifier,
                navController = navController,
                formController = mainViewModel.reportFormController,
                controller = mainViewModel.mediaGalleryController,
                enableSendButton = mainViewModel.enableSendButton.collectAsState(false).value,
                onSendRequest = mainViewModel::upload,
                onExitRequestFromHomeModule = {}
            )


        }
    }


}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun RootNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    formController: ReportFormController,
    controller: MediaPickersController,
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
            controller = controller
        )
        ReportFormNavGraph.graph(
            navGraphBuilder = this,
            controller = formController
        )


    }


}