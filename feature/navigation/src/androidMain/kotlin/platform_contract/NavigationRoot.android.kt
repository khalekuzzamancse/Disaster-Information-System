package platform_contract

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import core.di.DateUtilsProvider
import data_submission.ui.form.BaseDescriptionFormManager
import feature.home.HomeDestination
import feature.home.HomeViewModel
import image_picker.PhotoPickerAndroid
import image_picker.common.GalleryViewModel
import kotlinx.coroutines.launch
import routes.Destination
import components.NavLayoutDecorator
import routes.ReportFormRoute
import ui.PermissionIfNeeded
import video_picker.VideoGalleryGen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
actual fun NavigationRoot() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val homeViewModel = remember { HomeViewModel(context) }


    var selected by remember { mutableStateOf(Destination.HOME) }
    val imageGalleryViewModel = remember {
        GalleryViewModel()
    }
    val navController = rememberNavController()
    val videoGalleryViewModel = remember { GalleryViewModel() }
    LaunchedEffect(selected) {
        navController.popBackStack()
        try {
            navController.navigate(selected.toString())
        } catch (_: Exception) {

        }

    }

    val formManager = remember { BaseDescriptionFormManager(DateUtilsProvider.dateUtil) }
    NavLayoutDecorator(
        selected = selected,
        onDestinationSelected = { selected = it },
    ) {
        RootNavHost(
            modifier = Modifier,
            formManager = formManager,
            homeViewModel = homeViewModel,
            imageGalleryViewModel = imageGalleryViewModel,
            videoGalleryViewModel = videoGalleryViewModel,
            navController = navController,
            onSendRequest = {
                scope.launch {
                    homeViewModel.uploadImages(images = imageGalleryViewModel.galleryState.value.map { it.identity.id.toUri() })
                    //homeViewModel.uploadVideo(videos = videoGalleryViewModel.galleryState.value.map { it.identity.id.toUri() })
                }

            }
        )
    }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun RootNavHost(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    formManager: BaseDescriptionFormManager,
    imageGalleryViewModel: GalleryViewModel,
    videoGalleryViewModel: GalleryViewModel,
    navController: NavHostController,
    onSendRequest: () -> Unit,
) {

    val isUploading = homeViewModel.isUploading.collectAsState().value
    homeViewModel.progress.collectAsState().value
    val snackBarMessage = homeViewModel.snackBarMessage.collectAsState().value
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destination.HOME.toString()
    ) {
        composable(Destination.HOME.toString()) {
            Box {
                PermissionIfNeeded()
                HomeDestination(
                    snackBarMessage = snackBarMessage,
                    isSending = isUploading,
                    onSend = onSendRequest
                )

            }
        }
        composable(
            route = Destination.IMAGE_PICKER.toString(),
            enterTransition = { slideInHorizontally() + fadeIn() },
            exitTransition = { slideOutHorizontally() + fadeOut() }
        ) {
            PhotoPickerAndroid(
                imageGalleryViewModel = imageGalleryViewModel
            )
        }
        composable(
            route = Destination.REPORT_FORM.toString(),
            enterTransition = { slideInHorizontally() + fadeIn() },
            exitTransition = { slideOutHorizontally() + fadeOut() }
        ) {
            ReportFormRoute(formManager)
        }


        composable(
            route = Destination.VIDEO_PICKER.toString(),
            enterTransition = { slideInVertically { 1000 } + fadeIn() },
            exitTransition = { slideOutHorizontally() + fadeOut() }) {
            VideoGalleryGen(
                videoGalleryViewModel = videoGalleryViewModel
            )
        }

    }


}