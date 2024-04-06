package core.di

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import core.media_uploader.ImageUploader
import core.media_uploader.ImageUploaderImpl

object DependencyFactory {
    @Composable
    fun imageUploaderFactory(): ImageUploader {
        return ImageUploaderImpl(LocalContext.current)
    }
}


