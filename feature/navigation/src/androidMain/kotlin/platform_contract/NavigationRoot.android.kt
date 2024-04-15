package platform_contract

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
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
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import components.NavLayoutDecorator
import media_picker.di.MediaPickerFactory
import media_picker.ui.navigation.MediaPickerNavGraph
import media_picker.ui.pickers.MediaPickersController
import report_form.domain.ReportFormController
import report_form.ui.ReportFormNavGraph
import routes.Destination
import ui.CustomSnackBar
import ui.navigation.MainViewModel
import ui.navigation.MainViewModelFactory
import ui.navigation.Navigator


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
actual fun NavigationRoot() {
    LocalContext.current
    val navController = rememberNavController()
    val factory = MainViewModelFactory(MediaPickerFactory.mediaPickerController())
    val mainViewModel: MainViewModel = viewModel(factory = factory)
    RootDestination(
        mainViewModel = mainViewModel,
        navController = navController,
    )
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun RootDestination(
    mainViewModel: MainViewModel,
    navController: NavHostController,
) {

    val selected = mainViewModel.selected.collectAsState().value
    val enableSendButton = mainViewModel.enableSendButton.collectAsState(false).value
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
        modifier = Modifier.semantics(mergeDescendants = true) {
            contentDescription = "the app screen"
        },
        snackbarHost = {
            errorMessage?.let { message ->
                CustomSnackBar(message)
            }
        }
    ) {
        NavLayoutDecorator(
            modifier = Modifier
                .padding(it)
                .semantics(mergeDescendants = true) {
                    contentDescription = "Entire  screen"
                },
            selected = selected,
            onDestinationSelected = mainViewModel::onSelected,
        ) {
            NavHost(
                modifier = Modifier.semantics(mergeDescendants = true) {
                    contentDescription = "Entry point of apps"
                },
                route = null,
                navController = navController,
                startDestination = Destination.HOME.toString(),
                builder = {
                    navGraph(
                        formController = mainViewModel.reportFormController,
                        controller = mainViewModel.mediaGalleryController,
                        enableSendButton = enableSendButton,
                        onSendRequest = mainViewModel::upload,
                        onExitRequestFromHomeModule = { }
                    )
                }
            )

        }
    }


}

private fun NavGraphBuilder.navGraph(
    formController: ReportFormController,
    controller: MediaPickersController,
    enableSendButton: Boolean,
    onSendRequest: () -> Unit,
    onExitRequestFromHomeModule: () -> Unit,
) {
    composable(
        route = Destination.HOME.toString(),
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