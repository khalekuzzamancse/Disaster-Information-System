package com.khalekuzzaman.just.cse.datacollect


import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.BuildConfig


@Composable
fun SingleVideoPicker(){
    var uri by remember{
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
        }
    )

    val context = LocalContext.current

    Column(
    ){
            Button(onClick = {
                singlePhotoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                )

            }){
                Text("Pick Video")
            }
        val mediaMetadataRetriever = MediaMetadataRetriever()
        var thumbnail: Bitmap? = null
        try {
            mediaMetadataRetriever.setDataSource(context, uri)
            thumbnail = mediaMetadataRetriever.getFrameAtTime(100) // time in Micros
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        } finally {
            mediaMetadataRetriever.release()
        }

//        AsyncImage(model = thumbnail, contentDescription = null, modifier = Modifier.size(248.dp))

        uri?.let{
            VideoPlayerScreen(uri = it)
        }

    }
}