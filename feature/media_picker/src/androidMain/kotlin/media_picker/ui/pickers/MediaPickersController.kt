package media_picker.ui.pickers

import android.net.ConnectivityManager
import android.net.Uri
import androidx.core.net.toUri
import core.media_uploader.uploader.ImageUploadNotifierWorker
import core.work_manager.MultiplatformMedia
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import core.AndroidNetworkConnectivityObserver
import core.media_uploader.uploader.VideoUploadNotifierWorker
import ui.SnackBarMessage
import ui.SnackBarMessageType
import ui.media_picker.common.MediaGalleryController


/*
Checking the internet connection here,so that
 */
/**
 * Taking only the context,because if we have context then we can
 * create some other component,but if we do not inject context in that case
 * the client need to pass dependency as a result the client code need to extra dependency
 * such as WorkManger,and the other
 */

/**
 * Creating to for common,to avoid platform specific implementation
 *
 */
/**
 * * Used to provide the type safely of the file
 * * It is independent of the platform
 * platform dependent.In Android it is Uri.
 * * In android use uri.toString() to identity
 * * It will act as separate so this module does not need to implement other module
 * File format,so maintaining it own file format
 *
 */
/*

 */


/**
 * we are not holding the context,just for one time use
 * Making some field internal so that client does not access that info that the client do not need
 */
class MediaPickersController(
    connectivityManager:ConnectivityManager,
    private val imageUploadNotifierWorker: ImageUploadNotifierWorker,
    private val  videoUploadNotifierWorker: VideoUploadNotifierWorker,
) {
    private val connectivityObserver = AndroidNetworkConnectivityObserver(connectivityManager)
    private val _snackBarMessage = MutableStateFlow<SnackBarMessage?>(null)
    val errorMessage=_snackBarMessage.asStateFlow()
    internal val imageGalleryController = MediaGalleryController()
    internal val videoGalleryController = MediaGalleryController()


    fun upload() {
        CoroutineScope(Dispatchers.Default).launch {
            runIfInternetConnected {
                uploadImages(images = imageGalleryController.galleryState.value.map { it.identity.id.toUri() })
                uploadVideos(videos = videoGalleryController.galleryState.value.map { it.identity.id.toUri() })
            }
        }
    }


    //
    private fun uploadImages(images: List<Uri>) {
        val imageMedias = images.mapIndexed { index, uri ->
            MultiplatformMedia.Media("Image-${index + 1}", uri.toString())
        }
        imageUploadNotifierWorker.upload(imageMedias)

    }
    private fun uploadVideos(videos: List<Uri>) {
        val videosMedia = videos.mapIndexed { index, uri ->
            MultiplatformMedia.Media("Video-${index + 1}", uri.toString())
        }
        videoUploadNotifierWorker.upload(videosMedia)

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

