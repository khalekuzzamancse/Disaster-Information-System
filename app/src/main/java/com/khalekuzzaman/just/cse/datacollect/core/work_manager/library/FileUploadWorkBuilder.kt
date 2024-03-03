package com.khalekuzzaman.just.cse.datacollect.core.work_manager.library

import android.content.Context
import android.net.Uri
import androidx.lifecycle.asFlow
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.khalekuzzaman.just.cse.datacollect.core.work_manager.ImageUploadWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

/**
 * * This defined the useful methods,can be used as library to avoid reduce code
 */
class FileUploadWorkBuilder(
    private val workName: String = UUID.randomUUID().toString(),
    context: Context,
    private val uri: Uri,
    private val url: String,
) {
    private val workManager = WorkManager.getInstance(context)
    private val workRequest = createWorkRequest()
    val result: Flow<Result<String?>> = workManager.getWorkInfoByIdLiveData(workRequest.id)
            .asFlow().map { workInfo ->
                when (workInfo.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        Result.success(null)
                    }
                    WorkInfo.State.FAILED -> {
                        val message = workInfo.outputData.getString(WorkManagerConst.RESULT_MESSAGE)
                        Result.success(message)
                    }
                    else -> {
                        Result.success(null)
                    }
                }
            }

    fun upLoad() {
        cancelIfExit()
        workManager.enqueueUniqueWork(workName, ExistingWorkPolicy.REPLACE, workRequest)
    }

    private fun cancelIfExit() {
        workManager.cancelUniqueWork(workName)
    }

    private fun createInputData(): Data {
        return workDataOf(
            WorkManagerConst.API_URL to url,
            WorkManagerConst.URI to uri.toString()
        )

    }

    private fun createWorkRequest(): OneTimeWorkRequest {
        val inputData = createInputData()
        val constraints = Constraints.Builder()
            .build()
        return OneTimeWorkRequestBuilder<ImageUploadWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()
    }

}



