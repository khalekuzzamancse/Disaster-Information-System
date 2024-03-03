package com.khalekuzzaman.just.cse.datacollect.module_1.image_picker

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class GalleryMedia(
    val uri: Uri,
    val isSelected: Boolean = false
)
