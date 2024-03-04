package com.khalekuzzaman.just.cse.datacollect.core

import android.content.Context
import android.net.Uri
import com.khalekuzzaman.just.cse.datacollect.core.network.FilePoster
import com.khalekuzzaman.just.cse.datacollect.core.network.uriToByteArray
import core.network.NetworkPostRequest


object MediaUploader{
    suspend fun uploadImage(context: Context, uri: Uri, url: String): Result<String> {
        val byteArray = uriToByteArray(context, uri)
        return try {
            if (byteArray!=null){
                NetworkPostRequest.FilePost(url).upload(
                    fileType = NetworkPostRequest.FileType.IMAGE,
                    byteArray = byteArray)
            } else{
                Result.failure(Throwable("Failed to byte array null"))
            }
        }
        catch (ex:Exception){
            Result.failure(Throwable("Failed to :${ex.message}"))
        }

    }
    suspend fun uploadVideo(context: Context, uri: Uri, url: String): Result<String> {
        val byteArray = uriToByteArray(context, uri)
        return try {
            if (byteArray!=null){
                NetworkPostRequest.FilePost(url = url, fileName = "video").upload(fileType = NetworkPostRequest.FileType.VIDEO,byteArray = byteArray)
            } else{
                Result.failure(Throwable("Failed to byte array null"))
            }
        }
        catch (ex:Exception){
            Result.failure(Throwable("Failed to :${ex.message}"))
        }

    }

}