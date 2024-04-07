package upload.test

import core.media_uploader.MediaUploaderAndroid
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class MediaUploaderAndroidTest {
    /**
    Since need to access the Context  that  is why need instrumented test because context is initialized from  a device.
    So here Unit test will not work
     */
    @Test
    fun imageUploadTestingWithDrawableResUri() {
        runBlocking {
            val uri = DependencyFactory().getTestImageUri()
            val result = MediaUploaderAndroid().uploadImages(DependencyFactory().getContext(), uri)
            assertTrue(result.isSuccess)
        }
    }
    @Test
    fun videoUploadTestingWithDrawableResUri() {
        runBlocking {
            val uri = DependencyFactory().getTestVideoUri()
            val result = MediaUploaderAndroid().uploadVideo(DependencyFactory().getContext(), uri)
            assertTrue(result.isSuccess)
        }
    }


}