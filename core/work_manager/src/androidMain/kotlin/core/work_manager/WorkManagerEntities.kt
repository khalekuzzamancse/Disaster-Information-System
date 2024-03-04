package core.work_manager

import androidx.work.WorkInfo

object WorkManagerEntities {
    const val RESULT_MESSAGE = "message"

    data class WorkStatus(
        val workName: String,
        val status: WorkInfo.State
    ) {
        fun isSucceed():Boolean{
           return when (status) {
               WorkInfo.State.SUCCEEDED -> true
               else -> false
           }
        }
        fun isRunning()= status==WorkInfo.State.RUNNING
    }
}