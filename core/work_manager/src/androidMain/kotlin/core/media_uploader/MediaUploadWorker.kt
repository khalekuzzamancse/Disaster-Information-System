package core.media_uploader

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

/**
 * not holding the context,just one time use,so no  memory leak
 */

class MediaUploadWorker(
    context: Context
) {
    private val workManager = WorkManager.getInstance(context)
    suspend fun uploadMedia(mediaList: List<MediaCommon>, mediaType: MediaType) {
        mediaList.forEachIndexed { _, mediaItem ->
                // Prepare the input data
                val inputData = Data.Builder()
                    .putString(FileUploadWorker.URI,mediaItem.path)
                    .putString(FileUploadWorker.FILE_NAME, mediaItem.fileName)
                    .build()
                // Create a work request for the UploadWorker
                val workTag = "${mediaItem.fileName}${mediaType.prefix}"
                val workRequest = OneTimeWorkRequest.Builder(FileUploadWorker::class.java)
                    .addTag(workTag)
                    .setInputData(inputData)
                    .build()
                workManager.enqueueUniqueWork(workTag, ExistingWorkPolicy.REPLACE, workRequest)
            }

    }

}
