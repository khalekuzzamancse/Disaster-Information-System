package com.khalekuzzaman.just.cse.datacollect.core.work_manager.library

import android.net.Uri
import androidx.work.WorkInfo

object WorkManagerEntities {
    const val URI = "uri"
    const val API_URL = "url"
    const val RESULT_MESSAGE = "message"

    data class UploadableFile(
        val uri: Uri,
        val name: String
    )

    data class WorkStatus(
        val workName: String,
        val status: WorkInfo.State
    ) {
        fun isTerminated() = when (status) {
            WorkInfo.State.RUNNING, WorkInfo.State.ENQUEUED -> false
            else -> true
        }
    }
}