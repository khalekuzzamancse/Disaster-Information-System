package core.media_uploader.uploader
import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import core.media_uploader.worker.ImageUploadWorker
import core.media_uploader.worker.VideoUploadWorker
import core.work_manager.MultiplatformMedia

/**
 * Preventing client module to create the instance of it,
 * since it required context,to avoid complexity and reduce the responsibility if client
 */
class VideoUploadNotifierWorkerImpl internal constructor(context: Context):
    VideoUploadNotifierWorker {
    private val workManager = WorkManager.getInstance(context)
    override fun upload(mediaList: List<MultiplatformMedia.Media>) {
        val mediaType: MultiplatformMedia.MediaType=MultiplatformMedia.MediaType.VIDEO
        mediaList.forEachIndexed { _, mediaItem ->
            // Prepare the input data
            val inputData = Data.Builder()
                .putString(ImageUploadWorker.URI, mediaItem.id)
                .putString(ImageUploadWorker.FILE_NAME, mediaItem.fileName)
                .build()

            // Create a work request for the UploadWorker
            val workTag = "${mediaItem.fileName}${mediaType.prefix}"
            val workRequest = OneTimeWorkRequest.Builder(VideoUploadWorker::class.java)
                .addTag(workTag)
                .setInputData(inputData)
                .build()
            workManager.enqueueUniqueWork(workTag, ExistingWorkPolicy.REPLACE, workRequest)
        }

    }

}

