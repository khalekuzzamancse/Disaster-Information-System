package com.khalekuzzaman.just.cse.datacollect.core.work_manager.library

import android.content.Context
import android.net.Uri
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkQuery
import androidx.work.workDataOf
import kotlinx.coroutines.flow.map

/**
 * * This defined the useful methods,can be used as library to avoid reduce code
 */


import androidx.work.*

/**
 * @param workerClass should be called as,  WorkerClassName::class.java
 */
class FileUploadWorkBuilder(
    context: Context,
    private val url: String,
    private val workName: String,
    private val workerClass: Class<out ListenableWorker>,
    private val works: List<WorkManagerEntities.UploadableFile>
) {
    private val workManager = WorkManager.getInstance(context)
    private val workRequests = works.map(this::createWorkRequest)
    val workStatus = workManager
        .getWorkInfosFlow(WorkQuery.fromTags(works.map { it.name }))
        .map { works ->

            works.map {
               val res= it.outputData.getString(WorkManagerEntities.RESULT_MESSAGE)
                println("WorkMangerResult:$res")
                WorkManagerEntities.WorkStatus(
                    workName = it.tags.first().toString(), status = it.state
                )
            }.distinct()
        }

    fun upload() {
        cancelIfExit()
        workManager.enqueueUniqueWork(workName, ExistingWorkPolicy.REPLACE, workRequests)
    }

    private fun cancelIfExit() {
        workManager.cancelUniqueWork(workName)
    }

    private fun createWorkRequest(work: WorkManagerEntities.UploadableFile): OneTimeWorkRequest {
        val inputData = createInputData(work.uri)
        return OneTimeWorkRequest.Builder(workerClass)
            .setInputData(inputData)
            .addTag(work.name)
            .build()
    }

    private fun createInputData(uri: Uri): Data {
        return workDataOf(
            WorkManagerEntities.API_URL to url,
            WorkManagerEntities.URI to uri.toString()
        )
    }
}
