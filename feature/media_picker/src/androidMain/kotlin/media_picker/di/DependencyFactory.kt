package media_picker.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import core.di.DependencyFactory
import media_picker.ui.pickers.MediaPickersController

object DependencyFactory {
    @Composable
    fun mediaPickerController(): MediaPickersController {
        val context = LocalContext.current
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val imageUploader = DependencyFactory.imageUploaderFactory()
        return MediaPickersController(connectivityManager, imageUploader)
    }
}