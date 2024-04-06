package core.network

interface PostRequests{
    suspend fun upload(url: String, fileType: NetworkFileType, byteArray: ByteArray): Result<Unit>
}