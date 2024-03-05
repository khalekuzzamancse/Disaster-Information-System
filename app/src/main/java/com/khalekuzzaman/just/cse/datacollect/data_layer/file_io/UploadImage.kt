package com.khalekuzzaman.just.cse.datacollect.data_layer.file_io

import android.content.Context
import android.net.Uri
import core.network.NetworkRequest


object MediaUploader {
    private const val VIDEO_API = "http://192.168.10.154:8080/api/videos/upload"
    private const val IMAGE_API = "http://192.168.10.154:8080/api/images/upload"
    suspend fun uploadImage(context: Context, uri: Uri): Result<String> {
        val byteArray = uriToByteArray(context, uri)
        return try {
            if (byteArray != null) {
                NetworkRequest.FilePost(IMAGE_API).upload(
                    fileType = NetworkRequest.FileType.IMAGE,
                    byteArray = byteArray
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
                NetworkRequest.FilePost(url = VIDEO_API, fileName = "video")
                    .upload(fileType = NetworkRequest.FileType.VIDEO, byteArray = byteArray)
            } else {
                Result.failure(Throwable("Failed to byte array null"))
            }
        } catch (ex: Exception) {
            Result.failure(Throwable("Failed to :${ex.message}"))
        }

    }

}