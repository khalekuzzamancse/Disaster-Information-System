package com.khalekuzzaman.just.cse.datacollect.core.work_manager

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.WorkerParameters

class ImageUploadWork(
    private val context: Context
) {
    fun upLoad() {
        val workManager = WorkManager
            .getInstance(context)
            .enqueue(createWorkRequest())
    }

    private fun createWorkRequest(): WorkRequest {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        return OneTimeWorkRequestBuilder<ImageUpLoadWorker>()
            .setConstraints(constraints)
            .build()
    }

}



class ImageUpLoadWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            // Do something
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }
}