package core.media_uploader.uploader

import core.work_manager.MultiplatformMedia

interface ImageUploadNotifierWorker {
    fun upload(mediaList: List<MultiplatformMedia.Media>)
}