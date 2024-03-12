package core.media_uploader;

import android.net.Uri

/**
 * WorkManger media type,used to separate form platform code
 * the other module may can use their  own media representation to avoid coupling
 */
data class MediaItem(val fileName: String, val uri: Uri)


data class MediaCommon(val fileName: String, val path: String)
