package core.work_manager

import android.content.Context
import android.util.Log
import androidx.lifecycle.asFlow
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.Operation
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * * Separator between the android.work manager elements
 * * This class is made so that client module does not need to depended androidx.work manager internal classes,
 * the client  module need to just depends on the kotlin
 * * Client should is not required to create the instance of this class,so don't put additional parameters to the
 * constructor or method if it and it concrete class's
 *
 */
class SingleWorkBuilder(
    context: Context,
    private val taskName: String,
    work: Work,
) {

    private val workManager = WorkManager.getInstance(context)
    private val workRequest = createWorkRequest()
//    val workState: Flow<WorkManagerEntities.WorkStatus> =
//        workManager.getWorkInfoByIdFlow(workRequest.id).map {
//            WorkManagerEntities.WorkStatus(
//                workName = it.tags.first().toString(), status = it.state
//            )
//        }

    companion object {
        /**
         * It behave singleton,make sure sure you replace it for different instance
         * Right now this the only way to access the work performWork() without creating
         * [CustomWorker],and we must not create instance of [CustomWorker]
         */
        var performTask: suspend () -> Result<String> = {
            Result.failure(Throwable("Not initialize yet..."))
        }
    }

    init {
        performTask = work::doWork

    }

    suspend fun start():Flow<Boolean>{
        val operation = workManager.enqueue(workRequest)
        val res=operation.state.asFlow().map {
            it is Operation.State.SUCCESS
        }
        return res
        // Log.d("SingleWorkBuilder:","start:():$res")
    }

    private fun createWorkRequest(): OneTimeWorkRequest {
        return OneTimeWorkRequest
            .Builder(CustomWorker::class.java)
            .addTag(taskName)
            .build()
    }

}

/**
inner class is not allowed,how to access the perform task
We are not allowed to create the object of [CustomWorker]
 */
internal class CustomWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return SingleWorkBuilder.performTask().toWorkMangerResult()
    }
}


