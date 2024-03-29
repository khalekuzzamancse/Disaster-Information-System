package routes


enum class Destination(val order: Int) {
    HOME(0), REPORT_FORM(1), IMAGE_PICKER(2), VIDEO_PICKER(3),
    MediaPicker(2),
    //has the same order as image picker so that  it  can used as replacement of image picker and it selectable
    None(5)
}