package upload.test

import core.work_manager.MediaUploaderCommon
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class MediaUploaderCommonTest {
    //Avoid using space,causes problem on Android on DEX
    @Test
    fun testForImageUpload() = runBlocking {
        //Okay
        val byteArray = byteArrayOf(1, 2, 3, 4, 5, 6, 8, 10)
        val result = MediaUploaderCommon().uploadImage(byteArray)
        println("Result: $result")
        assertTrue(result.isSuccess)
    }
    //Avoid using space,causes problem on Android on DEX
    @Test
    fun testForVideoUpload() = runBlocking {
        val byteArray = byteArrayOf(1, 2, 3, 4, 5)
        val result = MediaUploaderCommon().uploadVideo(byteArray)
        println("Result: $result")
        assertTrue(result.isSuccess)
    }
}
