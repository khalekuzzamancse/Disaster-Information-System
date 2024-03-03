package com.khalekuzzaman.just.cse.datacollect.module_1.navigaion.screens.home

import android.content.Context
import android.net.Uri
import com.khalekuzzaman.just.cse.datacollect.core.MediaUploader.uploadImage
import com.khalekuzzaman.just.cse.datacollect.core.connectivity.ConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.core.connectivity.NetworkConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.core.work_manager.library.FileUploadWorkBuilder
import com.khalekuzzaman.just.cse.datacollect.module_1.chat_ui.SnackBarMessage
import com.khalekuzzaman.just.cse.datacollect.module_1.chat_ui.SnackBarMessageType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val context: Context, private val scope: CoroutineScope) {
    private val connectivityObserver = NetworkConnectivityObserver(context).observe()
    private var hasInternet: Boolean = false
    private val imageApiUrl = "http://192.168.10.154:8080/api/images/upload"
    private val videoApiURL = "http://192.168.10.154:8080/api/videos/upload"
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

    suspend fun uploadImages(images: List<Uri>) {
        if (!hasInternet) {
            SnackBarMessage("No Internet connection", SnackBarMessageType.Error).update()
            return
        }
        staratLoading()
        images.forEachIndexed { index, uri ->
            val res = FileUploadWorkBuilder(
                context = context,
                uri = uri,
                url = "http://192.168.10.154:8080/api/images/upload"
            ).upLoad()
//            val res = uploadImage(
//                context = context,
//                uri = uri,
//                url = imageApiUrl
//            )
            _progress.value = (index + 1) / images.size.toFloat()
//            if (res.isFailure) {
//                stopLoading()
//                val message = res.exceptionOrNull()?.message
//                SnackBarMessage("$message", SnackBarMessageType.Error).update()
//            } else {
//                SnackBarMessage("$res", SnackBarMessageType.Success).update()
//            }
        }
        stopLoading()

    }

    private fun stopLoading() {
        _isUploading.value = false
    }

    private fun staratLoading() {
        _isUploading.value = true
    }

    private suspend fun SnackBarMessage?.update() {
        _snackBarMessage.value = this
        delay(1000)
        _snackBarMessage.value = null
    }

    fun uploadVideo(videos: List<Uri>) {
        scope.launch {
            _isUploading.value = true
            videos.forEachIndexed { index, uri ->
                val res = uploadImage(context = context, uri = uri, url = videoApiURL)
                _progress.value = (index + 1) / videos.size.toFloat()
                if (res.isFailure) {
                    val message = res.exceptionOrNull()?.message
                    SnackBarMessage("$message", SnackBarMessageType.Error).update()
                } else {
                    SnackBarMessage("$res", SnackBarMessageType.Success).update()
                }
            }
            _isUploading.value = false
        }
    }
}
