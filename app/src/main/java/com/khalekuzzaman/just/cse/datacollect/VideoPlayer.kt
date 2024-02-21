package com.khalekuzzaman.just.cse.datacollect

import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem.fromUri
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView


@Composable
fun VideoPlayerScreen(uri: Uri) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(fromUri(uri))
            repeatMode = REPEAT_MODE_ONE
            prepare()
            play()
        }
    }
    DisposableEffect(
      key1 = Unit
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
    AndroidView(
        modifier = Modifier.fillMaxWidth().height(250.dp),
        factory = {
           PlayerView(context).apply {
                player = exoPlayer
                useController = true
                FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
    )
}