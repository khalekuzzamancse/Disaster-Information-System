package test

import core.network.NetworkFactory
import core.network.NetworkFileType
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.test.Test
import kotlin.test.assertTrue

class PostRequestsImplTest {

    private val postRequests = NetworkFactory.createPostRequester()

    @Test
    fun `invalid url testing`() = runBlocking {
        val url = "http://example.com/upload"
        val fileType = NetworkFileType.IMAGE
        val byteArray = byteArrayOf(1, 2, 3)
        val result = postRequests.upload(url, fileType, byteArray)
        println("Result: $result")
        assertTrue(result.isFailure)
    }
    @Test
    fun `valid url testing`() = runBlocking {
        val url = "http://192.168.0.154:8080/api/images/upload"
        val fileType = NetworkFileType.IMAGE
        val byteArray = byteArrayOf(1, 2, 3,4,5,6,8)
        val result = postRequests.upload(url, fileType, byteArray)
        println("Result: $result")
        assertTrue(result.isSuccess)
    }
    @Test
    fun `valid video url testing`() = runBlocking {
        val url = "http://192.168.0.154:8080/api/videos/upload"
        val fileType = NetworkFileType.VIDEO
        val byteArray = byteArrayOf(1, 2, 3,4,5,6,8,10)
        val result = postRequests.upload(url, fileType, byteArray)
        println("Result: $result")
        assertTrue(result.isSuccess)
    }
    @Test
    fun `image upload testing`() = runBlocking {
        val url = "http://192.168.0.154:8080/api/images/upload"
        val fileType = NetworkFileType.IMAGE
        val imageFile = File("C:\\Users\\Khalekuzzaman\\Desktop\\api.png")
        val byteArray = imageFile.readBytes()
        println("ByteArray:${byteArray.size}")
        val result = postRequests.upload(url, fileType, byteArray)
        println("Result: $result")
        assertTrue(result.isSuccess)
    }
}
