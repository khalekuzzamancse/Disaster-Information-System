package feature.home

import android.content.Context
import android.net.Uri
import core.network.NetworkFileType
import feature.home.data.connectivity.ConnectivityObserver
import feature.home.data.connectivity.NetworkConnectivityObserver
import feature.home.data.file_io.FileUploadHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.SnackBarMessage
import ui.SnackBarMessageType

class HomeViewModel(
    private val context: Context
){
    private val connectivityObserver = NetworkConnectivityObserver(context).observe()
    private var hasInternet: Boolean = false

    private val _snackBarMessage = MutableStateFlow<SnackBarMessage?>(null)
     val snackBarMessage = _snackBarMessage.asStateFlow()
     val uploader = FileUploadHelper("upload-work", fileType = NetworkFileType.IMAGE)
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

