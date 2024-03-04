package com.khalekuzzaman.just.cse.datacollect.core.work_manager

import android.content.Context
import androidx.work.WorkerParameters
import com.khalekuzzaman.just.cse.datacollect.core.MediaUploader
import com.khalekuzzaman.just.cse.datacollect.core.work_manager.library.FileUploaderWorker

/**
 * * Though it uses context ,but it does not hold the context
 * * Client should is not required to create the instance of this class
 */
class VideoUploadWorker(
    private val context: Context,
    params: WorkerParameters
) : FileUploaderWorker(context, params) {
    override suspend fun performWork(): Result {
        return if (uri != null && url != null) {
            val res = MediaUploader.uploadVideo(context, uri, url)
            toWorkManagerResult(res)
        } else {
            makeFailureResult("URL or Uri is null")
        }
    }
}

