package com.khalekuzzaman.just.cse.datacollect.core

import android.content.Context
import android.net.Uri
import com.khalekuzzaman.just.cse.datacollect.core.network.FilePoster
import com.khalekuzzaman.just.cse.datacollect.core.network.uriToByteArray


object MediaUploader{
    suspend fun uploadImage(context: Context, uri: Uri, url: String): Result<String> {
        val byteArray = uriToByteArray(context, uri)
        return try {
            if (byteArray!=null){
                FilePoster.FilePost(url).upload(fileType = FilePoster.FileType.IMAGE,byteArray = byteArray)
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
                FilePoster.FilePost(url = url, fileName = "video").upload(fileType = FilePoster.FileType.VIDEO,byteArray = byteArray)
            } else{
                Result.failure(Throwable("Failed to byte array null"))
            }
        }
        catch (ex:Exception){
            Result.failure(Throwable("Failed to :${ex.message}"))
        }

    }

}