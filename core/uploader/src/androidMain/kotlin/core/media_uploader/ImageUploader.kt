package core.media_uploader

import core.work_manager.MultiplatformMedia

interface ImageUploader {
    fun upload(mediaList: List<MultiplatformMedia.Media>, mediaType: MultiplatformMedia.MediaType)
}