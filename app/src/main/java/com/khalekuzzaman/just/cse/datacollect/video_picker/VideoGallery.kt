package com.khalekuzzaman.just.cse.datacollect.video_picker

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.datacollect.common_ui.VideoPlayerScreen
import com.khalekuzzaman.just.cse.datacollect.image_picker.GalleryMedia

@Composable
fun VideoGallery(
    videos: List<GalleryMedia>,
    onSelection: (Uri) -> Unit,
) {

    val state = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        modifier = Modifier,
        columns = StaggeredGridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp,
        state = state,
    ) {
        items(
            items = videos,
            itemContent = { video ->
                Box(
                    modifier = Modifier
                        .clickable {
                            onSelection(video.uri)
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 2.dp,
                            color = if (video.isSelected) Color.Red
                            else MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {
                    VideoPlayerScreen(
                        uri = video.uri
                    )
                    if (video.isSelected) {

                        Checkbox(
                            checked = true,
                            onCheckedChange = {
                                onSelection(video.uri)
                            },
                            modifier = Modifier.align(Alignment.BottomEnd)
                        )
                    }
                }

            }
        )
    }

}