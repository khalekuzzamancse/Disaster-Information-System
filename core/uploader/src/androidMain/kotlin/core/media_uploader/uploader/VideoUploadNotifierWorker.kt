package core.media_uploader.uploader

import core.work_manager.MultiplatformMedia

interface VideoUploadNotifierWorker {
    fun upload(mediaList: List<MultiplatformMedia.Media>)
}