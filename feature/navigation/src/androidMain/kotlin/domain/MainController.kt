package domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform_contracts.NetworkConnectivityObserver
import ui.SnackBarMessage
import ui.SnackBarMessageType


class MainController(
    private val connectivity: NetworkConnectivityObserver,
    private val mediaUploader: MediaUploaderCommon
) {

    private val _snackBarMessage = MutableStateFlow<SnackBarMessage?>(null)
    val snackBarMessage = _snackBarMessage.asStateFlow()
    val isUploading = mediaUploader.isUploading
    private suspend fun runIfInternetConnected(code: suspend () -> Unit) {
        if (!connectivity.isInternetAvailable()) {
            SnackBarMessage("No Internet connection", SnackBarMessageType.Error).update()
            return
        }
        code()
    }

    suspend fun uploadImages(images: List<MediaCommon>) {
        runIfInternetConnected {
            mediaUploader.uploadMedia(images, MediaTypeCommon.IMAGE)
        }
    }

    suspend fun uploadVideo(videos: List<MediaCommon>) {
        runIfInternetConnected {
            mediaUploader.uploadMedia(videos, MediaTypeCommon.IMAGE)
        }
    }

    suspend fun SnackBarMessage?.update() {
        _snackBarMessage.value = this
        delay(1000)
        _snackBarMessage.value = null
    }

}
