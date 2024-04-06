package core.media_uploader

import android.content.Context
import android.net.Uri
import core.work_manager.MediaUploaderCommon


internal object MediaUploaderAndroid {
    suspend fun uploadVideo(context: Context, uri: Uri): Result<Unit> {
        val byteArray = uriToByteArray(context, uri)
        return try {
            if (byteArray != null)
                MediaUploaderCommon().uploadVideo(byteArray)
            else
                Result.failure(Throwable("Failed to byte array null at:MediaUploaderAndroid"))

        } catch (ex: Exception) {
            Result.failure(Throwable("Failed to :${ex.message}"))
        }

    }

    suspend fun uploadImages(context: Context, uri: Uri): Result<Unit> {
        val byteArray = uriToByteArray(context, uri)
        return try {
            if (byteArray != null)
                MediaUploaderCommon().uploadImage(byteArray)
            else
                Result.failure(Throwable("Failed to byte array null at:MediaUploaderAndroid"))

        } catch (ex: Exception) {
            Result.failure(Throwable("Failed to :${ex.message}"))
        }

    }

    private fun uriToByteArray(context: Context, imageUri: Uri): ByteArray? {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        return inputStream?.use { it.readBytes() } // Automatically closes the InputStream after use
    }


}

