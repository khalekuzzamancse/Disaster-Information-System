package com.khalekuzzaman.just.cse.datacollect.data_layer.file_io

import android.content.Context
import android.net.Uri
import core.network.components.NetworkFileType
import platform_contracts.HTTPRequests


object MediaUploader {
    private const val VIDEO_API = "http://192.168.10.154:8080/api/videos/upload"
    private const val IMAGE_API = "http://192.168.10.154:8080/api/images/upload"
    suspend fun uploadImage(context: Context, uri: Uri): Result<String> {
        val byteArray = uriToByteArray(context, uri)
        return try {
            if (byteArray != null) {
                HTTPRequests.uploadFile(
                    url = IMAGE_API,
                    fileType = NetworkFileType.IMAGE,
                    byteArray = byteArray,

                )
            } else {
                Result.failure(Throwable("Failed to byte array null"))
            }
        } catch (ex: Exception) {
            Result.failure(Throwable("Failed to :${ex.message}"))
        }

    }

    suspend fun uploadVideo(context: Context, uri: Uri): Result<String> {
        val byteArray = uriToByteArray(context, uri)
        return try {
            if (byteArray != null) {
                HTTPRequests.uploadFile(url = VIDEO_API, fileType = NetworkFileType.VIDEO, byteArray = byteArray)
            } else {
                Result.failure(Throwable("Failed to byte array null"))
            }
        } catch (ex: Exception) {
            Result.failure(Throwable("Failed to :${ex.message}"))
        }

    }

}