package com.khalekuzzaman.just.cse.datacollect.navigaion

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khalekuzzaman.just.cse.datacollect.chat_ui.ConversionScreen
import com.khalekuzzaman.just.cse.datacollect.common_ui.PermissionIfNeeded
import com.khalekuzzaman.just.cse.datacollect.image_picker.MultiplePhotoPicker
import com.khalekuzzaman.just.cse.datacollect.video_picker.VideoGallery

object Destinations {
    const val HOME = "Home"
    const val IMAGE = "ImageGallery"
    const val VIDEO = "VideoGallery"
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RootNavHost() {
    val navController = rememberNavController()
    val gotoHome: () -> Unit = {
        navController.popBackStack()
    }
    NavHost(
        navController = navController,
        startDestination = Destinations.HOME
    ) {
        composable(Destinations.HOME) {
            Box() {
                PermissionIfNeeded()
                ConversionScreen(
                    onVideoPickRequest = {
                        navController.navigate(Destinations.VIDEO)
                    },
                    onImagePickRequest = {
                        navController.navigate(Destinations.IMAGE)
                    }
                )
            }
        }
        composable(Destinations.IMAGE) {
            MultiplePhotoPicker(
                onExitRequest = gotoHome
            )
        }
        composable(Destinations.VIDEO) {
            VideoGallery(onExitRequest =gotoHome)
        }

    }

}