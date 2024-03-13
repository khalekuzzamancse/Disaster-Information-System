package ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import androidx.core.net.toUri
import core.media_uploader.MediaItem
import core.media_uploader.MediaType
import core.media_uploader.WorkMangerWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import platform_contracts.AndroidNetworkConnectivityObserver
import ui.image_picker.common.MediaGalleryController


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
    context: Context,
) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val connectivityObserver = AndroidNetworkConnectivityObserver(connectivityManager)
    private val _snackBarMessage = MutableStateFlow<SnackBarMessage?>(null)
    val errorMessage=_snackBarMessage.asStateFlow()
    internal val imageGalleryController = MediaGalleryController()
    internal val videoGalleryController = MediaGalleryController()
    private val worker = WorkMangerWorker(context)

    fun upload() {
        CoroutineScope(Dispatchers.Default).launch {
            runIfInternetConnected {
                uploadImages(images = imageGalleryController.galleryState.value.map { it.identity.id.toUri() })
                uploadVideo(videos = videoGalleryController.galleryState.value.map { it.identity.id.toUri() })
            }
        }
    }


    //
    private suspend fun uploadImages(images: List<Uri>) {
        val imageMedias = images.mapIndexed { index, uri ->
            MediaItem("Image-${index + 1}", uri)
        }
        worker.uploadMedia(imageMedias, MediaType.IMAGE)

    }

    private suspend fun uploadVideo(videos: List<Uri>) {
        val videoMedias = videos.mapIndexed { index, uri ->
            MediaItem("Video-${index + 1}", uri)
        }
        worker.uploadMedia(videoMedias, MediaType.VIDEO)
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

