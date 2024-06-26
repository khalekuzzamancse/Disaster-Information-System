package media_picker.ui.pickers

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import ui.media_picker.common.KMPFile


@Composable
internal fun VideoPreviewer(file: KMPFile) {
    val context = LocalContext.current
    val mediaMetadataRetriever = MediaMetadataRetriever()
    var thumbnail: Bitmap? = null
    try {
        val uri=file.id.toUri()//make sure android.core.network Uri
        mediaMetadataRetriever.setDataSource(context, uri)
        thumbnail = mediaMetadataRetriever.getFrameAtTime(2_000) // time in Micros
    } catch (_: Exception) {

    } finally {
        mediaMetadataRetriever.release()
    }
    AsyncImage(model = thumbnail, contentDescription = "video thumbnail", modifier = Modifier)
}
