package domain

/**
 * @param path is platform specif such as Uri,in case of android
 *

 */
data class MediaCommon(
    val fileName: String, val path: String
)