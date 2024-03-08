package com.khalekuzzaman.just.cse.datacollect.ui_layer

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
import androidx.core.net.toUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import feature.home.HomeScreen
import image_picker.MultiplePhotoPickerGen
import image_picker.common.GalleryViewModelG
import image_picker.video_picker.VideoGalleryGen
import ui.PermissionIfNeeded

object Destinations {
    const val HOME = "Home"
    const val IMAGE_PICKER = "ImageGallery"
    const val VIDEO_PICKER = "VideoGallery"
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
        GalleryViewModelG()
    }
    LocalContext.current
    val videoGalleryViewModel = remember { GalleryViewModelG() }


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
                        images = imageGalleryViewModel.galleryState.collectAsState().value.map { it.identity.id.toUri() },//make sure androidx.core.uri
                        videos = videoGalleryViewModel.galleryState.collectAsState().value.map { it.identity.id.toUri()},
                        onVideoPickRequest = {
                            navController.navigate(Destinations.VIDEO_PICKER)
                        }
                    ) {
                        navController.navigate(Destinations.IMAGE_PICKER)
                    }
                }
            }
            composable(
                route = Destinations.IMAGE_PICKER,
                enterTransition = { slideInHorizontally() + fadeIn() },
                exitTransition = { slideOutHorizontally() + fadeOut() }
            ) {
                MultiplePhotoPickerGen(
                    onExitRequest = gotoHome,
                    imageGalleryViewModelG = imageGalleryViewModel
                )
            }

            composable(
                route = Destinations.VIDEO_PICKER,
                enterTransition = { slideInVertically { 1000 } + fadeIn() },
                exitTransition = { slideOutHorizontally() + fadeOut() }) {
                VideoGalleryGen(
                    onExitRequest = gotoHome,
                    videoGalleryViewModel = videoGalleryViewModel
                )
            }

        }


    }

}