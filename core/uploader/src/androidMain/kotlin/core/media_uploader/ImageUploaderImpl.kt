package core.media_uploader
import android.content.Context
import android.net.Uri
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import core.nofication_manager.IndeterminateProgressNotification
import core.work_manager.MultiplatformMedia
import kotlin.random.Random

/**
 * Preventing client module to create the instance of it,
 * since it required context,to avoid complexity and reduce the responsibility if client
 */
class ImageUploaderImpl internal constructor(context: Context):ImageUploader {
    private val workManager = WorkManager.getInstance(context)
    override fun upload(mediaList: List<MultiplatformMedia.Media>, mediaType: MultiplatformMedia.MediaType) {
        mediaList.forEachIndexed { _, mediaItem ->
            // Prepare the input data
            val inputData = Data.Builder()
                .putString(ImageUploadWorker.URI, mediaItem.id)
                .putString(ImageUploadWorker.FILE_NAME, mediaItem.fileName)
                .build()

            // Create a work request for the UploadWorker
            val workTag = "${mediaItem.fileName}${mediaType.prefix}"
            val constraints=Constraints.Builder().setRequiredNetworkType(NetworkType.METERED).build()
            val workRequest = OneTimeWorkRequest.Builder(ImageUploadWorker::class.java)
                .addTag(workTag)
                .setInputData(inputData)
                .build()
            workManager.enqueueUniqueWork(workTag, ExistingWorkPolicy.REPLACE, workRequest)
        }

    }

}

/*
WorkManger state is not as the same as your function return result.
The work manager state tells that work is enqueued or not,it the work is run or not.
to understand the behaviour of it is so confusing specially the doWork() method return result
and the WorkManger work observe.
so that is why we are direcly going to

Understand the WorkInfo.State
Running->If the currently the doWork() method is running
Success->if the work is success already even in the past,or now it is success,
since the work manager keep the history and save the work so that it can run later if needed
that is why it also keep track the past history,so Success will give you also the work that is
Success in the past(store in database)

The best fit in our case to ,we will show the notification directly in the doWork() method,
or we can show notification,if the current state is Running.
Right now the easiest solution is that direcly show the notification on doWork() method,
because then we can guaranty that the notification will show when the task is running,and when the task
if failed we can show the notification of it with deep link.

If you want to observer the task that are failed then you can observe them by only filtering them as following
 workManager.getWorkInfo'sFlow(WorkQuery.fromStates(WorkInfo.State.RUNNING)

 */

internal class ImageUploadWorker(private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    companion object {
        const val URI = "FILE_URI"
        const val FILE_NAME = "FILE_NAME"
    }

    private val notificationId = Random.nextInt()
    private var notificationTitle: String = "${inputData.getString(FILE_NAME)}"

    private val notification = IndeterminateProgressNotification(
        notificationId, notificationTitle, "⌛", context,
    )


    override suspend fun doWork(): Result {

        notification.start()
        // Get the input URI
        val imageUriString = inputData.getString(URI)
        val imageUri = Uri.parse(imageUriString)
        val res = MediaUploaderAndroid.uploadImages(
            context,
            imageUri,
        )
        // Indicate whether the work finished successfully with the Result
        return if (res.isSuccess) {
            notification.stop(message = "✅")
            Result.success(Data.Builder().putString("Result", "success").build())
        } else {
            notification.stop(message = "❌")
            Result.failure(Data.Builder().putString("Result", "success").build())
        }

    }

}
