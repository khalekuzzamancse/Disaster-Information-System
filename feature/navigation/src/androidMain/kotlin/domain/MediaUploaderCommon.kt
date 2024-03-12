package domain

import kotlinx.coroutines.flow.Flow

/**
 * Do not using expect actual because we may have multiple implementation
 * so that is why using Interface
 */
interface  MediaUploaderCommon {
    val isUploading: Flow<Boolean>
    suspend fun uploadMedia(
        mediaList: List<MediaCommon>,
        mediaType: MediaTypeCommon
    )
}