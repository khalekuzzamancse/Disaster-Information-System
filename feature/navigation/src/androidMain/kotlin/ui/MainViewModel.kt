package ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import core.di.DateUtilsProvider
import core.media_uploader.MediaItem
import core.media_uploader.MediaType
import core.media_uploader.WorkMangerWorker
import data_submission.ui.form.ReportFormControllerImpl
import image_picker.common.GalleryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import platform_contracts.AndroidNetworkConnectivityObserver
import routes.Destination

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

class MainViewModelFactory(
    private val context: Context,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

/**
 * we are not holding the context,just for one time use
 */
class MainViewModel(
    context: Context,
) : ViewModel() {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val connectivityObserver = AndroidNetworkConnectivityObserver(connectivityManager)
    private val _snackBarMessage = MutableStateFlow<SnackBarMessage?>(null)
    val reportFormController = ReportFormControllerImpl(DateUtilsProvider.dateUtil)
    val imageViewModel = GalleryViewModel()
    val videoViewModel = GalleryViewModel()
    val snackBarMessage = _snackBarMessage.asStateFlow()
    private val _splashScreenShowing = MutableStateFlow(true)
    val splashScreenShowing = _splashScreenShowing.asStateFlow()
    private val _enableSendButton = MutableStateFlow(true)
    private val isFormValid: Flow<Boolean> =
        reportFormController.fromState.map { it.areAllFieldsFilled() }

    val enableSendButton =
        combine(_enableSendButton, isFormValid) { enable, valid -> enable && valid }


    private val worker = WorkMangerWorker(context)
    private val _selectedDestination = MutableStateFlow(Destination.HOME)
    val selected = _selectedDestination.asStateFlow()

    fun onSelected(destination: Destination) {
        _selectedDestination.update { destination }
    }

    //
    init {
        viewModelScope.launch {
            delay(5_000)
            _splashScreenShowing.update { false }
        }
    }

    fun upload() {
        viewModelScope.launch {
            runIfInternetConnected {
                disableSendButtonTemporary()
                uploadImages(images = imageViewModel.galleryState.value.map { it.identity.id.toUri() })
                uploadVideo(videos = videoViewModel.galleryState.value.map { it.identity.id.toUri() })
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

    private suspend fun disableSendButtonTemporary() {
        _enableSendButton.update { false }
        delay(2000)
        _enableSendButton.update { true }
    }


}

