package core.work_manager

import core.network.NetworkFactory
import core.network.NetworkFileType

/**
 * This function is designed to facilitate unit testing by eliminating the dependency on Context and Uri. It accepts a
 * byte array as a parameter, allowing the class to be utilized in a multiplatform context. This design ensures the
 * class's functionality can be independently tested across different platforms.
 */
internal class MediaUploaderCommon {
    private val VIDEO_API = "http://192.168.0.154:8080/api/videos/upload"
    private val IMAGE_API = "http://192.168.0.154:8080/api/images/upload"

 private val requestSender = NetworkFactory.createPostRequester()
    suspend fun uploadVideo(byteArray: ByteArray): Result<Unit> {
        return requestSender.upload(url = VIDEO_API, fileType = NetworkFileType.VIDEO, byteArray = byteArray)
    }

    suspend fun uploadImage(byteArray: ByteArray): Result<Unit> {
        return requestSender.upload(url = IMAGE_API, fileType = NetworkFileType.IMAGE, byteArray = byteArray)
    }
}

