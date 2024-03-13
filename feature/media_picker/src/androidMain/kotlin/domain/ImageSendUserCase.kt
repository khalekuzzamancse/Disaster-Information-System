package domain

import android.net.Uri
import core.media_uploader.MediaItem
import core.media_uploader.MediaType
import core.media_uploader.WorkMangerWorker

class ImageSendUserCase(
    private val worker: WorkMangerWorker,
    private val images: List<Uri>,
) {
     suspend fun upload() {
        val imageMedias = images.mapIndexed { index, uri ->
            MediaItem("Image-${index + 1}", uri)
        }
        worker.uploadMedia(imageMedias, MediaType.IMAGE)

    }
}