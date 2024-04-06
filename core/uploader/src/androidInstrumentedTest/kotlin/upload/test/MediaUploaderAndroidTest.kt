package upload.test

import android.content.ContentResolver
import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
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
    fun testingWithDrawableResources() {
        runBlocking {
            val context = InstrumentationRegistry.getInstrumentation().targetContext//need to run a device in order to get context
            with(context) {
                val resourceId = core.work_manager.test.R.drawable.testing_photo
                val uri = Uri.Builder()
                    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                    .authority(resources.getResourcePackageName(resourceId))
                    .appendPath(resources.getResourceTypeName(resourceId))
                    .appendPath(resources.getResourceEntryName(resourceId))
                    .build()
               val result=MediaUploaderAndroid.uploadImages(context, uri)
                assertTrue(result.isSuccess)
            }
        }


    }
}