package com.khalekuzzaman.just.cse.datacollect

import android.content.Context
import android.net.Uri
import com.khalekuzzaman.just.cse.datacollect.network.uploadImageByteArray
import com.khalekuzzaman.just.cse.datacollect.network.uploadVideoByteArray
import com.khalekuzzaman.just.cse.datacollect.network.uriToByteArray


suspend fun uploadImage(context: Context, uri: Uri, url: String): Result<String> {
    val byteArray = uriToByteArray(context, uri)
    return uploadImageByteArray(url, byteArray)
}

suspend fun uploadVideo(context: Context, uri: Uri, url: String): Result<String> {
    val byteArray = uriToByteArray(context, uri)
    return uploadVideoByteArray(url, byteArray)
}

