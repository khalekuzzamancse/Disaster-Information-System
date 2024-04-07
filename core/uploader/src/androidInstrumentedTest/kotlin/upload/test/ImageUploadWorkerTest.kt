package upload.test

import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import core.media_uploader.worker.ImageUploadWorker
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class ImageUploadWorkerTest {

    /**
    The Work manger use the database and enqueue work,so to take the effect,you may need to run/relaunch the application manually
    after running the test,then the work will be done.
    even after running the apps,it may takes some times to take the work start or finish.
    try it multiple time.
    if not work try to run all the test of that class,instead of single test.
    It may takes longer times ....than you expected
     */
    @Test
    fun testUsingRawUri() {
        runBlocking {
            val workManager = WorkManager.getInstance(DependencyFactory().getContext())
            val uri = DependencyFactory().getTestImageUri()

            // Prepare the input data
            val inputData = Data.Builder()
                .putString(ImageUploadWorker.URI, uri.toString())
                .putString(ImageUploadWorker.FILE_NAME, "testImage")
                .build()
            // Create a work request for the UploadWorker
            val workTag = "${"testImage"}${"jpg"}"
            val workRequest = OneTimeWorkRequest.Builder(ImageUploadWorker::class.java)
                .addTag(workTag)
                .setInputData(inputData)
                .build()
            Log.d(
                "TestLogging",
                "${workRequest.id}"
            )//use the TestFunction Logcat instead of the androidStudio Logcat to see output
//     workManager.enqueueUniqueWork(workTag, ExistingWorkPolicy.REPLACE, workRequest)
            //should  not use unique work for test,because unique work saved to database so it will not trigger multiple time,until you remove and reinstall the apps
            workManager.enqueue(workRequest)
        }

    }

}