package image_picker.common
import androidx.compose.runtime.Immutable

/**
 * Represent the Video Or Image identity,
 * @param identity in case of Android it Uri.so convert it to uri when you are android
 */
@Immutable
data class GalleryMediaGeneric(
    val identity: KMPFile,
    val isSelected: Boolean = false
)
