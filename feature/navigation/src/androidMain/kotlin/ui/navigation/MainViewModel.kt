package ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import media_picker.ui.pickers.MediaPickersController
import report_form.platform_contracts.ReportFormFactory
import routes.Destination
import ui.SnackBarMessage

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
    private val mediaGalleryController: MediaPickersController,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(mediaGalleryController) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

/**
 * we are not holding the context,just for one time use
 */
class MainViewModel(
    val mediaGalleryController: MediaPickersController,
) : ViewModel() {

    private val _snackBarMessage = MutableStateFlow<SnackBarMessage?>(null)
    val reportFormController = ReportFormFactory.createFormController()

    val snackBarMessage =
        combine(_snackBarMessage, mediaGalleryController.errorMessage) { msg1, msg2 ->
            msg1 ?: msg2
        }
    private val _splashScreenShowing = MutableStateFlow(true)
    val splashScreenShowing = _splashScreenShowing.asStateFlow()
    private val _enableSendButton = MutableStateFlow(true)
    private val isFormValid = reportFormController.isFormValid

    val enableSendButton = combine(_enableSendButton, isFormValid) { enable, valid ->
        //enable && valid
        true
    }


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
        //run asynchronous  to provide the button disable as well as upload
        CoroutineScope(Dispatchers.Default).launch {
            disableSendButtonTemporary()
        }
        CoroutineScope(Dispatchers.Default).launch {
            mediaGalleryController.upload()
        }

    }


    private suspend fun disableSendButtonTemporary() {
        _enableSendButton.update { false }
        delay(2000)
        _enableSendButton.update { true }
    }


}

