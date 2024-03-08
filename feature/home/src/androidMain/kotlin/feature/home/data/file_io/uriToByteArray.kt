package com.khalekuzzaman.just.cse.datacollect.data_layer.file_io

import android.content.Context
import android.net.Uri

fun uriToByteArray(context: Context, imageUri: Uri): ByteArray? {
    val inputStream = context.contentResolver.openInputStream(imageUri)
    return inputStream?.use { it.readBytes() } // Automatically closes the InputStream after use
}
