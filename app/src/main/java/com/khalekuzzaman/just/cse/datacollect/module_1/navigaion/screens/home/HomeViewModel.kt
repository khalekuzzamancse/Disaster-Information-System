package com.khalekuzzaman.just.cse.datacollect.module_1.navigaion.screens.home

import android.content.Context
import android.net.Uri
import android.util.Log
import com.khalekuzzaman.just.cse.datacollect.core.MediaUploader
import com.khalekuzzaman.just.cse.datacollect.core.connectivity.ConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.core.connectivity.NetworkConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.module_1.chat_ui.SnackBarMessage
import com.khalekuzzaman.just.cse.datacollect.module_1.chat_ui.SnackBarMessageType
import core.work_manager.SingleWorkBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class HomeViewModel(private val context: Context) {
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
            //  println("WorksFlowInfo:Uploading:${isUploading.value}")

        }
    }


    suspend fun uploadImages(images: List<Uri>) {
        if (!hasInternet) {
            SnackBarMessage("No Internet connection", SnackBarMessageType.Error).update()
            return
        }
        UploadImageHelper(images).upload(context)
    }

    suspend fun uploadVideo(videos: List<Uri>) {
        if (!hasInternet) {
            SnackBarMessage("No Internet connection", SnackBarMessageType.Error).update()
            return
        }
        val workBuilder = SingleWorkBuilder(
            context = context,
            taskName = "demo",
            work = {
                val uri = videos.first()
                MediaUploader.uploadVideo(context, uri)
            }
        )
//        workBuilder.workState.collect {
//            Log.d("CustomWorker:State", "$it")
//        }

    }

    private suspend fun SnackBarMessage?.update() {
        _snackBarMessage.value = this
        delay(1000)
        _snackBarMessage.value = null
    }

}

private class UploadImageHelper(val images: List<Uri>) {
    /**
     * Not holding the context,to make garbage collection easy
     */

    suspend fun upload(context: Context) {
        images.forEachIndexed { index, uri ->
            upload(context = context, uri = uri, taskName = "$index")
        }
    }

    suspend fun upload(context: Context, uri: Uri, taskName: String) {
        val workBuilder = SingleWorkBuilder(
            context = context,
            taskName = taskName,
            work = {
                MediaUploader.uploadImage(context, uri)
            },
        )
        workBuilder.start()
//        val res = workBuilder.start().collect {
//            Log.d("UploadImageHelper:State", "$it")
//        }
        //  Log.d("UploadImageHelper:State", "$res")
//         workBuilder.workState.collect {
//            Log.d("UploadImageHelper:State", "$it")
//        }
    }

}
