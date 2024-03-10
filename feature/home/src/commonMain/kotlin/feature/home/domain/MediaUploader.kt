package feature.home.domain

import kotlinx.coroutines.flow.Flow

/**
 * * [isSending] -> for observing  currently sending or not,based of notification ,progressbar will be shown or
 * some functionality can be disable while sending
 * * [sentByPercentage] -> used to keep track how many percent are sent,based on a progress bar can be shown
 * * [upload] -> is made as suspend so that both sync and async code can be used
 */
interface MediaUploader {
    val isSending: Flow<Boolean>
    val sentByPercentage: Flow<Float>

    suspend fun upload(): Boolean

}