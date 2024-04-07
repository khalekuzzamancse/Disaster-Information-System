package core.di

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import core.media_uploader.uploader.ImageUploadNotifierWorker
import core.media_uploader.uploader.ImageUploadNotifierWorkerImpl
import core.media_uploader.uploader.VideoUploadNotifierWorker
import core.media_uploader.uploader.VideoUploadNotifierWorkerImpl

object UploaderFactory {
    @Composable
    fun imageUploaderFactory(): ImageUploadNotifierWorker {
        return ImageUploadNotifierWorkerImpl(LocalContext.current)
    }
    @Composable
    fun videoUploaderFactory(): VideoUploadNotifierWorker {
        return VideoUploadNotifierWorkerImpl(LocalContext.current)
    }
}


