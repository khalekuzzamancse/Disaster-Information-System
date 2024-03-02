package com.khalekuzzaman.just.cse.datacollect.navigaion

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khalekuzzaman.just.cse.datacollect.chat_ui.HomeScreen
import com.khalekuzzaman.just.cse.datacollect.common_ui.PermissionIfNeeded
import com.khalekuzzaman.just.cse.datacollect.image_picker.GalleryViewModel
import com.khalekuzzaman.just.cse.datacollect.image_picker.MultiplePhotoPicker
import com.khalekuzzaman.just.cse.datacollect.video_picker.VideoGallery

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
    val videoGalleryViewModel = remember { GalleryViewModel() }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destinations.HOME
    ) {
        composable(Destinations.HOME) {
            Box() {
                PermissionIfNeeded()
                HomeScreen(
                    pickedImageCount = imageGalleryViewModel.galleryState.collectAsState().value.size,
                    pickedVideoCount = videoGalleryViewModel.galleryState.collectAsState().value.size,
                    onVideoPickRequest = {

                        navController.navigate(Destinations.VIDEOPICKER)
                    },
                    onImagePickRequest = {
                        navController.navigate(Destinations.IMAGEPICKER)
                    }
                )
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