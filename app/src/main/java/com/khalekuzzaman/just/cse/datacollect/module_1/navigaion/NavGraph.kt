package com.khalekuzzaman.just.cse.datacollect.module_1.navigaion

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khalekuzzaman.just.cse.datacollect.module_1.common_ui.PermissionIfNeeded
import com.khalekuzzaman.just.cse.datacollect.module_1.image_picker.GalleryViewModel
import com.khalekuzzaman.just.cse.datacollect.module_1.image_picker.MultiplePhotoPicker
import com.khalekuzzaman.just.cse.datacollect.module_1.navigaion.screens.home.HomeScreen
import com.khalekuzzaman.just.cse.datacollect.module_1.video_picker.VideoGallery

object Destinations {
    const val HOME = "Home"
    const val IMAGEPICKER = "ImageGallery"
    const val VIDEOPICKER = "VideoGallery"
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val gotoHome: () -> Unit = {
        navController.popBackStack()
    }

    val imageGalleryViewModel = remember {
        GalleryViewModel()
    }
    LocalContext.current
    val videoGalleryViewModel = remember { GalleryViewModel() }


    Box {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Destinations.HOME
        ) {
            composable(Destinations.HOME) {
                Box() {
                    PermissionIfNeeded()
                    HomeScreen(
                        images = imageGalleryViewModel.galleryState.collectAsState().value.map { it.uri },
                        videos =  videoGalleryViewModel.galleryState.collectAsState().value.map { it.uri },
                        onVideoPickRequest = {

                            navController.navigate(Destinations.VIDEOPICKER)
                        }
                    ) {
                        navController.navigate(Destinations.IMAGEPICKER)
                    }
                }
            }
            composable(
                route = Destinations.IMAGEPICKER,
                enterTransition = { slideInHorizontally() + fadeIn() },
                exitTransition = { slideOutHorizontally() + fadeOut() }
            ) {
                MultiplePhotoPicker(
                    onExitRequest = gotoHome,
                    imageGalleryViewModel = imageGalleryViewModel
                )
            }

            composable(
                route = Destinations.VIDEOPICKER,
                enterTransition = { slideInVertically { 1000 } + fadeIn() },
                exitTransition = { slideOutHorizontally() + fadeOut() }) {
                VideoGallery(onExitRequest = gotoHome, videoGalleryViewModel = videoGalleryViewModel)
            }

        }


    }

}