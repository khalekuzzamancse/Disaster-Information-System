package core.work_manager.unused

import androidx.work.Data
import androidx.work.ListenableWorker

internal fun Result<String>.toWorkMangerResult(): ListenableWorker.Result {
    val message = this.exceptionOrNull()?.message
    return if (this.isFailure)
        ListenableWorker.Result.failure(
            Data.Builder().putString(WorkManagerEntities.RESULT_MESSAGE, "$message").build()
        )
    else
        ListenableWorker.Result.success(
            Data.Builder().putString(WorkManagerEntities.RESULT_MESSAGE, "success").build()
        )
}

