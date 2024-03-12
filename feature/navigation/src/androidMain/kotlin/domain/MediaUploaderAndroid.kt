package domain

import android.content.Context
import core.media_uploader.MediaType
import core.media_uploader.MediaUploadWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Not holding the context,it is just once time used,so it is memory safe
 */
class MediaUploaderAndroid(
    context: Context
) : domain.MediaUploaderCommon {
    override val isUploading: Flow<Boolean> =MutableStateFlow(false)
    private  val worker = MediaUploadWorker(context)
    override suspend fun uploadMedia(mediaList: List<domain.MediaCommon>, mediaType: domain.MediaTypeCommon) {
        val imageMedias = mediaList.mapIndexed { index, path ->
            core.media_uploader.MediaCommon("Image-${index + 1}", path.path)
        }
        worker.uploadMedia(imageMedias, MediaType.IMAGE)
    }
}