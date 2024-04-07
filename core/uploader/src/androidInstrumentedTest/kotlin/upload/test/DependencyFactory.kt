package upload.test

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry

internal class DependencyFactory {
    fun getTestImageUri(): Uri {
        val resourceId = core.work_manager.test.R.drawable.testing_photo
        return buildUriFromResourceId(getContext(), resourceId)
    }
    fun getTestVideoUri(): Uri {
        val resourceId = core.work_manager.test.R.raw.test_video
        return buildUriFromResourceId(getContext(), resourceId)
    }

    private fun buildUriFromResourceId(context: Context, resourceId: Int): Uri {
        return Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(context.resources.getResourcePackageName(resourceId))
                .appendPath(context.resources.getResourceTypeName(resourceId))
                .appendPath(context.resources.getResourceEntryName(resourceId))
                .build()
    }
     fun getContext(): Context {
        //need to run a device in order to get context,that is why does not work with unit test
        return InstrumentationRegistry.getInstrumentation().targetContext
    }
}
