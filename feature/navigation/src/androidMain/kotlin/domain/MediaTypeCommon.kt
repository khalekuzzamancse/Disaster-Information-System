package domain;

/**
 * Defining the media type for this module
 * so that it does not need to implement from other module
 */
enum class MediaTypeCommon(val prefix: String) {
    IMAGE("Image"),
    VIDEO("Video");
}
