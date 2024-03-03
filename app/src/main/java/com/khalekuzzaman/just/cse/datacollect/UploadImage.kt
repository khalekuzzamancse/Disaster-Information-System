package com.khalekuzzaman.just.cse.datacollect

import android.content.Context
import android.net.Uri
import com.khalekuzzaman.just.cse.datacollect.network.FileUploader
import com.khalekuzzaman.just.cse.datacollect.network.UploadFileType
import com.khalekuzzaman.just.cse.datacollect.network.uploadMediaByteArray
import com.khalekuzzaman.just.cse.datacollect.network.uriToByteArray


suspend fun uploadImage(context: Context, uri: Uri, url: String): Result<String> {
    val byteArray = uriToByteArray(context, uri)
    return try {
        if (byteArray!=null){
            FileUploader().uploadImageByteArray(url = url, byteArray = byteArray)
        } else{
            Result.failure(Throwable("Failed to byte array null"))
        }
    }
    catch (ex:Exception){
        Result.failure(Throwable("Failed to :${ex.message}"))
    }

}

suspend fun uploadFile(context: Context, uri: Uri, url: String, uploadFileType:UploadFileType): Result<String> {
    val byteArray = uriToByteArray(context, uri)
    return uploadMediaByteArray(url = url, byteArray = byteArray, uploadFileType = uploadFileType)
}


