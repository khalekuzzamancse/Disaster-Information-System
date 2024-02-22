package com.khalekuzzaman.just.cse.datacollect

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.khalekuzzaman.just.cse.datacollect.common_ui.PermissionIfNeeded
import com.khalekuzzaman.just.cse.datacollect.image_picker.MultiplePhotoPicker
import com.khalekuzzaman.just.cse.datacollect.ui.theme.AppTheme
import com.khalekuzzaman.just.cse.datacollect.video_picker.SingleVideoPicker
import com.khalekuzzaman.just.cse.datacollect.video_picker.VideoGallery

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme{
               // MultiplePhotoPicker()
                PermissionIfNeeded()
                //SingleVideoPicker()
                VideoGallery()

            }
        }
    }
}

