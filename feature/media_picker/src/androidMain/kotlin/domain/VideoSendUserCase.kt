package domain

import android.net.Uri
import core.media_uploader.MediaItem
import core.media_uploader.MediaType
import core.media_uploader.WorkMangerWorker

class VideoSendUserCase(
    private val worker: WorkMangerWorker,
    private val videos: List<Uri>,
) {
     suspend fun uploadVideo() {
        val videoMedias = videos.mapIndexed { index, uri ->
            MediaItem("Video-${index + 1}", uri)
        }
        worker.uploadMedia(videoMedias, MediaType.VIDEO)
    }
}