package com.khalekuzzaman.just.cse.datacollect.ui_layer.common_ui

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.BuildConfig
import androidx.media3.common.util.UnstableApi
import coil3.compose.AsyncImage


@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(uri: Uri) {
    println("PlayerURi:$uri")
    val context = LocalContext.current
//    val exoPlayer = ExoPlayer.Builder(context).build().apply {
//            setMediaItem(fromUri(uri))
//            this.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
//            play()
//            prepare()
//    }
    val mediaMetadataRetriever = MediaMetadataRetriever()
    var thumbnail: Bitmap? = null
    try {
        mediaMetadataRetriever.setDataSource(context, uri)
        thumbnail = mediaMetadataRetriever.getFrameAtTime(2000) // time in Micros
    } catch (e: Exception) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        }
    } finally {
        mediaMetadataRetriever.release()
    }

    AsyncImage(model = thumbnail, contentDescription = null, modifier = Modifier)
//    AndroidView(
//        modifier = Modifier
//            .wrapContentWidth()
//            .heightIn(max = 120.dp),
//        factory = {
//            PlayerView(context).apply {
//                player = exoPlayer
//                FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//                )
//                controllerShowTimeoutMs = 1000
//                useController = true
//                hideController()
//                this.player?.volume = 0f
//            }
//        }
//    )
}
