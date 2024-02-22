package com.khalekuzzaman.just.cse.datacollect.common_ui

sealed  class FileExtension(
    val mimeType: String,
    val ext: String,
    val encodingByte: Byte
){
    override fun toString(): String {
        return "FileExtension(mimeType='$mimeType', ext='$ext', encodingByte=$encodingByte)"
    }
}


object FileExtensions {

    private const val TEXT = "text/plain"
    private const val IMAGE_JPEG = "image/jpeg"
    private const val IMAGE_PNG = "image/png"
    private const val VIDEO_MP4 = "video/mp4"
    private const val DOCUMENT_PDF = "application/pdf"


    object TXT : FileExtension(
        mimeType = TEXT,
        ext = "txt",
        encodingByte = 1
    )

    object JPEG : FileExtension(
        mimeType = IMAGE_JPEG,
        ext = "jpg",
        encodingByte = 2
    )

    object PNG : FileExtension(
        mimeType = IMAGE_PNG,
        ext = "png",
        encodingByte = 3
    )

    object MP4 : FileExtension(
        mimeType = VIDEO_MP4,
        ext = "mp4",
        encodingByte = 4
    )


    object PDF : FileExtension(
        mimeType = DOCUMENT_PDF,
        ext = "pdf",
        encodingByte = 6
    )

    fun getFileExtension(mimeType: String): FileExtension? {
        return when (mimeType) {
            TEXT -> TXT
            IMAGE_JPEG -> JPEG
            IMAGE_PNG -> PNG
            VIDEO_MP4 -> MP4
            DOCUMENT_PDF -> PDF
            else -> null
        }
    }
    fun getMimeType(byte: Byte): String? {
        return when (byte) {
            TXT.encodingByte -> TXT.mimeType
            JPEG.encodingByte -> JPEG.mimeType
            PNG.encodingByte -> PNG.mimeType
            MP4.encodingByte -> MP4.mimeType
            PDF.encodingByte -> PDF.mimeType
            else -> null
        }
    }

}