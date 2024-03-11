package feature.home.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import core.media_uploader.MediaItem
import core.media_uploader.MediaType
import core.media_uploader.WorkMangerWorker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform_contracts.AndroidNetworkConnectivityObserver
import ui.SnackBarMessage
import ui.SnackBarMessageType

/*
Checking the internet connection here,so that
 */
class HomeViewModelAndroid(
    context: Context
) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val connectivityObserver = AndroidNetworkConnectivityObserver(connectivityManager)
    private val _snackBarMessage = MutableStateFlow<SnackBarMessage?>(null)
    val snackBarMessage = _snackBarMessage.asStateFlow()
    val isUploading = MutableStateFlow(false)
    private val worker = WorkMangerWorker(context)
    suspend fun uploadImages(images: List<Uri>) {
        runIfInternetConnected {
            val imageMedias = images.mapIndexed { index, uri ->
                MediaItem("Image-${index + 1}", uri)
            }
            worker.uploadMedia(imageMedias, MediaType.IMAGE)
        }
    }

    suspend fun uploadVideo(videos: List<Uri>) {
        runIfInternetConnected {
            val videoMedias = videos.mapIndexed { index, uri ->
                MediaItem("Video-${index + 1}", uri)
            }
            worker.uploadMedia(videoMedias, MediaType.VIDEO)
        }

    }
    private fun isReportFormFilled(){

    }


    private suspend fun runIfInternetConnected(code: suspend () -> Unit) {
        if (!connectivityObserver.isInternetAvailable()) {
            SnackBarMessage("No Internet connection", SnackBarMessageType.Error).update()
            return
        }
        code()
    }

    private suspend fun SnackBarMessage?.update() {
        _snackBarMessage.value = this
        delay(1000)
        _snackBarMessage.value = null
    }


}

