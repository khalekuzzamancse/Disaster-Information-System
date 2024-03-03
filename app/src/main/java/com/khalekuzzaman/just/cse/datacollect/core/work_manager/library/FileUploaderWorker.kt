package com.khalekuzzaman.just.cse.datacollect.core.work_manager.library

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters

/**
 * * Though it uses context ,but it does not hold the context
 * * Client should is not required to create the instance of this class
 */
abstract class FileUploaderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    protected val url = this.inputData.getString(WorkManagerConst.API_URL)
    private val uriString = inputData.getString(WorkManagerConst.URI)
    protected val uri = uriString?.let { Uri.parse(it) }


    override suspend fun doWork(): Result {
        Log.d("WorkManagerTest: ", "doWork():Start")
        return try {
            performWork()
        } catch (e: Exception) {
            makeFailureResult(e)
        }
    }

    abstract suspend fun performWork(): Result

    //Helper methods
    protected fun toWorkManagerResult(result: kotlin.Result<String>): Result {
        val message = result.exceptionOrNull()?.message
        return if (result.isFailure)
            Result.failure(
                Data.Builder().putString(WorkManagerConst.RESULT_MESSAGE, "$message").build()
            )
        else
            Result.success(
                Data.Builder().putString(WorkManagerConst.RESULT_MESSAGE, "success").build()
            )
    }

    private fun makeFailureResult(ex: Exception?): Result {
        val message = ex?.message
        return Result.failure(Data.Builder().putString("message", "$message}").build())
    }

    protected fun makeFailureResult(message: String): Result {
        return Result.failure(Data.Builder().putString("message", "$message}").build())
    }

}