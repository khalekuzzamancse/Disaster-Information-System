package feature.home.ui


import feature.home.domain.MediaUploader
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import ui.SnackBarMessage
import ui.SnackBarMessageType

/**
 * * Encapsulate the common logic,so that all platform can used it ,with platform specific code
 *
 */
open class HomeBaseViewModel(
    private val imageUploader: MediaUploader,
    private val videoUploader: MediaUploader,
) {
    private val _snackBarMessage = MutableStateFlow<SnackBarMessage?>(null)
    open val snackBarMessage = _snackBarMessage.asStateFlow()
    open val progress = combine(
        imageUploader.sentByPercentage,
        videoUploader.sentByPercentage
    ) { imageSent, videoSent ->
//        (imageSent + videoSent) / 2f
        imageSent
    }
    open val isUploading =
        combine(imageUploader.isSending, videoUploader.isSending) { imageSending, videoSending ->
            imageSending || videoSending
        }

    open suspend fun uploadImages() {
        val isUploaded = imageUploader.upload()
        if (!isUploaded) {
            SnackBarMessage("Not uploaded", SnackBarMessageType.Error).update()
            return
        }
    }

   open suspend fun uploadVideo() {
        val isUploaded = videoUploader.upload()
        if (!isUploaded) {
            SnackBarMessage("Not uploaded", SnackBarMessageType.Error).update()
            return
        }
    }

    private suspend fun SnackBarMessage?.update() {
        _snackBarMessage.value = this
        delay(1000)
        _snackBarMessage.value = null
    }

}



