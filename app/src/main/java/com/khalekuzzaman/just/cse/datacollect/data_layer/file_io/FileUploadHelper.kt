package com.khalekuzzaman.just.cse.datacollect.data_layer.file_io

import android.content.Context
import android.net.Uri
import core.network.NetworkRequest
import core.nofication_manager.ProgressNotificationBuilder
import core.work_manager.SingleWorkBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class FileUploadHelper(
    private val taskName: String,
    private val fileType: NetworkRequest.FileType,
) {
    /**
     * Not holding the context,to make garbage collection easy
     */
    private lateinit var notificationBuilder: ProgressNotificationBuilder

    //
    private var totalItem = 0
    private val _sentItems = MutableStateFlow<List<Uri>>(emptyList())
    private val _sentByPercentage = MutableStateFlow(0f)
    private val _isSending = MutableStateFlow(false)
    val sentByPercentage = _sentByPercentage.asStateFlow()
    val isSending = _isSending.asStateFlow()

    /**
     * Taking the [Context] as local parameter to make garbage collection easier
     */
    suspend fun upload(context: Context, files: List<Uri>) {
        buildNotificationBuilder(context, files.size)
        totalItem = files.size
        val workBuilder = SingleWorkBuilder(
            context = context,
            taskName = taskName,
            work = {
                onSendingStart()
                files.forEachIndexed { index, uri ->
                    val result = uploadFile(context, uri)
                    onResult(index, uri, result)
                }
                onAllSetOrTerminated()

                returnSuccessToWorkManger()
            },
        )
        workBuilder.start()
    }

    private fun buildNotificationBuilder(context: Context, target: Int) {
        notificationBuilder = ProgressNotificationBuilder(
            title = "Uploading", message = "image uploading",
            id = Random.nextInt(), target = target, context = context
        )
    }

    private suspend fun uploadFile(context: Context, uri: Uri): Result<String> {
        return when (fileType) {
            NetworkRequest.FileType.IMAGE -> MediaUploader.uploadImage(context, uri)
            NetworkRequest.FileType.VIDEO -> MediaUploader.uploadImage(context, uri)
        }
    }

    private fun returnSuccessToWorkManger(): Result<String> {
        return Result.success("")
    }

    private fun onResult(index: Int, uri: Uri, result: Result<String>) {
        if (result.isSuccess) {
            updateSentItem(uri)
            updatePercentage()
            updateNotificationProgressbar(index + 1)
        }
    }

    private fun updateSentItem(uri: Uri) {
        _sentItems.update { sentItems -> sentItems + uri }

    }

    private fun updateNotificationProgressbar(current: Int) {
        notificationBuilder.updateProgress(current)
    }

    private fun updatePercentage() {
        _sentByPercentage.update {
            try {
                //diving by 0 can causes crash
                (_sentItems.value.size * 1f) / totalItem
            } catch (_: Exception) {
                0f
            }
        }
    }

    private suspend fun onAllSetOrTerminated() {
        _sentByPercentage.update { 1f }
        delay(1000)
        _isSending.update { false }
    }

    private fun onSendingStart() {
        updateNotificationProgressbar(0)
        _isSending.update { true }
    }


}
