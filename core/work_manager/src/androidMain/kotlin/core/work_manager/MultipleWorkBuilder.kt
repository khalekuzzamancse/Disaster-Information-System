package core.work_manager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters

/**
 * * Separator between the android.work manager elements
 * * This class is made so that client module does not need to depended androidx.work manager internal classes,
 * the client  module need to just depends on the kotlin
 * * Client should is not required to create the instance of this class,so don't put additional parameters to the
 * constructor or method if it and it concrete class's
 *
 */
class MultipleWorkBuilder(
    context: Context,
    private val taskName: String,
    takes: List<Work>,
) {
    private val workManager = WorkManager.getInstance(context)
    private val workRequests = createWorkRequest()

    companion object {
        /**
         * It behave singleton,make sure sure you replace it for different instance
         * Right now this the only way to access the work performWork() without creating
         * [MultipleWorker],and we must not create instance of [MultipleWorker]
         */
        var works: List<Work> = emptyList()
    }

    init {
        works = takes
        start()
    }

    private fun start() {
        workManager.enqueue(workRequests)
    }

    private fun createWorkRequest(): OneTimeWorkRequest {
        return OneTimeWorkRequest
            .Builder(MultipleWorker::class.java)
            .addTag(taskName)
            .build()
    }

}

/**
inner class is not allowed,how to access the perform task
We are not allowed to create the object of [MultipleWorker]
 */
internal class MultipleWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return SingleWorkBuilder.performTask().toWorkMangerResult()
    }
}


