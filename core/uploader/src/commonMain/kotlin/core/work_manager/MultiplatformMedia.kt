package core.work_manager


interface MultiplatformMedia {
    /**
     * * This is used to represent the generic media for multiplatform
     * @param id is the identifier such as Uri for android,and the filepath for windows
     */
    data class Media(val fileName: String, val id:String)
    enum class MediaType(val prefix: String) {
        IMAGE("Image"), VIDEO("Video");
    }

}


