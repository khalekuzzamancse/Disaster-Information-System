package com.khalekuzzaman.just.cse.datacollect.core

import android.content.Context
import android.net.Uri
import com.khalekuzzaman.just.cse.datacollect.core.network.FilePoster
import com.khalekuzzaman.just.cse.datacollect.core.network.uploadMediaByteArray
import com.khalekuzzaman.just.cse.datacollect.core.network.uriToByteArray


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
