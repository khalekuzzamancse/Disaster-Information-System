package com.khalekuzzaman.just.cse.datacollect.core.network

import android.content.Context
import android.net.Uri

suspend fun uriToByteArray(context: Context, imageUri: Uri): ByteArray? {
    val inputStream = context.contentResolver.openInputStream(imageUri)
    return inputStream?.use { it.readBytes() } // Automatically closes the InputStream after use
}
