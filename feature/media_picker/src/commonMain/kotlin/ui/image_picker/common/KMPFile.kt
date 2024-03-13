package ui.image_picker.common

/**
 * * Used to provide the type safely of the file
 * * It is independent of the platform
 * @param id is platform dependent.In Android it is Uri.
 * * In android use uri.toString() to identity
 *
 *
 */
@PublishedApi
internal data class KMPFile(
    val id:String
)
