package com.khalekuzzaman.just.cse.datacollect.ui_layer.navigaion.screens.home

import android.content.Context
import android.net.Uri
import com.khalekuzzaman.just.cse.datacollect.data_layer.connectivity.ConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.data_layer.connectivity.NetworkConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.data_layer.file_io.FileUploadHelper
import com.khalekuzzaman.just.cse.datacollect.ui_layer.chat_ui.SnackBarMessage
import com.khalekuzzaman.just.cse.datacollect.ui_layer.chat_ui.SnackBarMessageType
import core.network.NetworkFileType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val context: Context) {
    private val connectivityObserver = NetworkConnectivityObserver(context).observe()
    private var hasInternet: Boolean = false

    private val _snackBarMessage = MutableStateFlow<SnackBarMessage?>(null)
    val snackBarMessage = _snackBarMessage.asStateFlow()
    private val uploader = FileUploadHelper("upload-work", fileType = NetworkFileType.IMAGE)
    val progress = uploader.sentByPercentage
    val isUploading = uploader.isSending

    init {
        CoroutineScope(Dispatchers.Default).launch {
            connectivityObserver.collect {
                hasInternet = it == ConnectivityObserver.Status.Available
            }

        }
    }


    suspend fun uploadImages(images: List<Uri>) {
        if (!hasInternet) {
            SnackBarMessage("No Internet connection", SnackBarMessageType.Error).update()
            return
        }
        uploader.upload(context, images)
    }

    suspend fun uploadVideo(videos: List<Uri>) {
        if (!hasInternet) {
            SnackBarMessage("No Internet connection", SnackBarMessageType.Error).update()
            return
        }
        uploader.upload(context, videos)

    }

    private suspend fun SnackBarMessage?.update() {
        _snackBarMessage.value = this
        delay(1000)
        _snackBarMessage.value = null
    }

}

