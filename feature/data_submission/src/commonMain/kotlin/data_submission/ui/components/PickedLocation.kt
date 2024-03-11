package data_submission.ui.components

/**
 * Used to decouple the Underlying LatLong class of Google Map
 */
data class PickedLocation(
    val lat: Double,
    val lang: Double
){
    override fun toString(): String {
        return "lat:$lat , long:$lang"
    }
}
