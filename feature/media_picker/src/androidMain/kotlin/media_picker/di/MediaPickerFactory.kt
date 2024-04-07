package media_picker.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import core.di.UploaderFactory
import media_picker.ui.pickers.MediaPickersController

object MediaPickerFactory {
    @Composable
    fun mediaPickerController(): MediaPickersController {
        val context = LocalContext.current
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val imageUploader = UploaderFactory.imageUploaderFactory()
        val videoUploader=UploaderFactory.videoUploaderFactory()
        return MediaPickersController(
            connectivityManager = connectivityManager,
            imageUploadNotifierWorker = imageUploader,
            videoUploadNotifierWorker = videoUploader
        )
    }
}