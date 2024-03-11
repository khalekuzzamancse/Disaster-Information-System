package platform_contract

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally

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
import components.NavLayoutDecorator
import core.di.DateUtilsProvider
import data_submission.ui.form.BaseDescriptionFormManager
import feature.home.ui.HomeViewModelAndroid

import feature.home.ui.TeacherAboutUs

import image_picker.PhotoPickerAndroid
import image_picker.common.GalleryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import routes.Destination
import routes.ReportFormRoute
import routes.SplashScreen

import video_picker.VideoGallery

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
actual fun NavigationRoot() {
    var showSlashScreen by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(5_000)
        showSlashScreen = false
    }
    if (showSlashScreen) {
        SplashScreen()
    } else {
        RootDestination()

    }


}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun RootDestination() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val homeViewModelAndroid = remember { HomeViewModelAndroid(context) }
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
            homeViewModelAndroid = homeViewModelAndroid,
            imageGalleryViewModel = imageGalleryViewModel,
            videoGalleryViewModel = videoGalleryViewModel,
            navController = navController,
            onSendRequest = {
                scope.launch {
                    homeViewModelAndroid.uploadImages(images = imageGalleryViewModel.galleryState.value.map { it.identity.id.toUri() })
                    homeViewModelAndroid.uploadVideo(videos = videoGalleryViewModel.galleryState.value.map { it.identity.id.toUri() })
                }


            },
            onAboutUs = {
                try {
                    navController.navigate(Destination.ABOUT_US.toString())
                } catch (_: Exception) {

                }
            }
        )
    }


}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun RootNavHost(
    modifier: Modifier = Modifier,
    homeViewModelAndroid: HomeViewModelAndroid,
    formManager: BaseDescriptionFormManager,
    imageGalleryViewModel: GalleryViewModel,
    videoGalleryViewModel: GalleryViewModel,
    navController: NavHostController,
    onAboutUs: () -> Unit,

    onSendRequest: () -> Unit,
) {

    val isUploading = homeViewModelAndroid.isUploading.collectAsState().value
    val snackBarMessage = homeViewModelAndroid.snackBarMessage.collectAsState().value
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
            HomeDestination(
                    snackBarMessage = snackBarMessage,
                    isSending = isUploading,
                    onSendRequest = onSendRequest,
                    onAboutUsRequest = onAboutUs

                )
        }
        composable(
            route = Destination.IMAGE_PICKER.toString(),
            enterTransition = { slideInHorizontally() + fadeIn() },
            exitTransition = { slideOutHorizontally() + fadeOut() }
        ) {
            PhotoPickerAndroid(
                viewModel = imageGalleryViewModel
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
            exitTransition = { slideOutHorizontally() + fadeOut() }
        ) {
            VideoGallery(
                viewModel = videoGalleryViewModel
            )
        }
        composable(
            route = Destination.ABOUT_US.toString(),
            enterTransition = { slideInVertically { 1000 } + fadeIn() },
            exitTransition = { slideOutHorizontally() + fadeOut() }) {
            TeacherAboutUs()

        }

    }


}