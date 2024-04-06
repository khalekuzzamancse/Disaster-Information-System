package core.network
data class Header(val key: String, val value: String)
/**
 * @param key is the key or file type that used  in the back-end while defining the end-point/api/rest controller
 * @param contentType used to the Client to make the request body/header
 */
enum class NetworkFileType(val contentType: String, val key: String) {
    VIDEO(contentType = "video/mp4", key = "video"), IMAGE(contentType = "image/jpeg", key = "image");
}
