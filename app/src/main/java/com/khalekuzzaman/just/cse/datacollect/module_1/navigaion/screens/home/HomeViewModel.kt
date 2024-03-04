package com.khalekuzzaman.just.cse.datacollect.module_1.navigaion.screens.home

import android.content.Context
import android.net.Uri
import androidx.work.WorkInfo
import com.khalekuzzaman.just.cse.datacollect.core.connectivity.ConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.core.connectivity.NetworkConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.core.work_manager.ImageUploadWorker
import com.khalekuzzaman.just.cse.datacollect.core.work_manager.VideoUploadWorker

import com.khalekuzzaman.just.cse.datacollect.core.work_manager.library.FileUploadWorkBuilder
import com.khalekuzzaman.just.cse.datacollect.core.work_manager.library.WorkManagerEntities
import com.khalekuzzaman.just.cse.datacollect.module_1.chat_ui.SnackBarMessage
import com.khalekuzzaman.just.cse.datacollect.module_1.chat_ui.SnackBarMessageType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val context: Context, private val scope: CoroutineScope) {
    private val connectivityObserver = NetworkConnectivityObserver(context).observe()
    private var hasInternet: Boolean = false
    private val _progress = MutableStateFlow(0f)
    val progress = _progress.asStateFlow()
    private val _isUploading = MutableStateFlow(false)
    val isUploading = _isUploading.asStateFlow()
    private val _snackBarMessage = MutableStateFlow<SnackBarMessage?>(null)
    val snackBarMessage = _snackBarMessage.asStateFlow()


    init {
        CoroutineScope(Dispatchers.Default).launch {
            connectivityObserver.collect {
                hasInternet = it == ConnectivityObserver.Status.Available
            }

        }
    }
    init {
        CoroutineScope(Dispatchers.Default).launch {
            println("WorksFlowInfo:Uploading:${isUploading.value}")

        }
    }


    suspend fun uploadImages(images: List<Uri>) {
        if (!hasInternet) {
            SnackBarMessage("No Internet connection", SnackBarMessageType.Error).update()
            return
        }
        uploadImage(context = context, uris = images, workName = "01").collect { works ->
            works.forEach { work ->
                println("WorksFlowInfo:${work}")
            }

           _isUploading.value = works.any { it.status==WorkInfo.State.RUNNING }

        }


    }

    suspend fun uploadVideo(videos: List<Uri>) {
        if (!hasInternet) {
            SnackBarMessage("No Internet connection", SnackBarMessageType.Error).update()
            return
        }
        uploadVideos(context = context, uris = videos, workName = "vidoes").collect { works ->
            //if at least one task is running,then uploading
            _isUploading.value = works.any{it.isRunning()}
            val totalNeedToSent=videos.size
            val totalSent=works.count { it.isSucceed()}
            println("WorksFlowInfo:(totalNeed:$totalNeedToSent ,sent:$totalSent)")

            works.forEach { work ->
                println("WorksFlowInfo:$work")

            }

        }

    }

    private suspend fun SnackBarMessage?.update() {
        _snackBarMessage.value = this
        delay(1000)
        _snackBarMessage.value = null
    }

}

/**
 * Work name is required to make unique ,so that by mistake the same
 * work does not enqueue multiple time
 */
private fun uploadImage(context: Context, uris: List<Uri>, workName: String)
        : Flow<List<WorkManagerEntities.WorkStatus>> {
    val builder = FileUploadWorkBuilder(
        context = context,
        url = "http://192.168.10.154:8080/api/images/upload",
        works = uris.mapIndexed { index, uri ->
            WorkManagerEntities.UploadableFile(
                uri = uri,
                name = "$index"
            )
        },
        workName = workName,
        workerClass = ImageUploadWorker::class.java
    )
    builder.upload()
    return builder.workStatus
}

/**
 * Work name is required to make unique ,so that by mistake the same
 * work does not enqueue multiple time
 */
private fun uploadVideos(context: Context, uris: List<Uri>, workName: String)
        : Flow<List<WorkManagerEntities.WorkStatus>> {
    val builder = FileUploadWorkBuilder(
        context = context,
        url = "http://192.168.10.154:8080/api/videos/upload",
        works = uris.mapIndexed { index, uri ->
            WorkManagerEntities.UploadableFile(
                uri = uri,
                name = "$index"
            )
        },
        workName = workName,
        workerClass = VideoUploadWorker::class.java
    )
    builder.upload()
    return builder.workStatus
}
