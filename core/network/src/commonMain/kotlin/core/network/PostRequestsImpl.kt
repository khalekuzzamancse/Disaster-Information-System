package core.network

import io.ktor.client.HttpClient
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import java.net.ConnectException

/**
 * This class restricts direct instantiation by clients in order to provide a factory method
 * for obtaining instances. This design enables the provision of different implementations.
 */

class PostRequestsImpl internal constructor() : PostRequests {
    private val httpClient = HttpClient()
    override suspend fun upload(
        url: String,
        fileType: NetworkFileType,
        byteArray: ByteArray
    ): Result<Unit> {
        return try {
            val formData = createFormData(fileType, byteArray)
            submitFormData(url, formData)
        } catch (ex: Exception) {
            Result.failure(Throwable(RequestCodeAnalyzer.getReason(ex)))
        } finally {
            close()
        }
    }


    private fun createFormData(fileType: NetworkFileType, byteArray: ByteArray): List<PartData> {
        return formData {
            append(key = fileType.key, value = byteArray, headers = Headers.build {
                append(
                    name = HttpHeaders.ContentType,
                    value = fileType.contentType
                )
                append(HttpHeaders.ContentDisposition, "filename=${fileType.key}")
            })
        }
    }

    private suspend fun submitFormData(url: String, data: List<PartData>): Result<Unit> {
        val response = httpClient.submitFormWithBinaryData(
            url = url,
            formData = data
        ) {
            method = HttpMethod.Post
        }
        val isSuccess = response.status == HttpStatusCode.OK
        return if (isSuccess)
            Result.success(Unit)
        else
            Result.failure(Throwable(RequestCodeAnalyzer.getReason(response.status)))
    }

    private fun handleException(exception: Exception): Throwable {
        return when (exception) {
            is ConnectException -> Throwable("Failed to connect to the server.")
            is SocketTimeoutException -> Throwable("Connection timed out.")
            is ClientRequestException -> Throwable("Client request error: ${exception.response.status.description}")
            is ServerResponseException -> Throwable("Server error: ${exception.response.status.description}")
            is RedirectResponseException -> Throwable("Unexpected redirect: ${exception.response.status.description}")
            else -> Throwable("Unknown error occurred: ${exception.message}")
        }
    }

    private fun close() {
        httpClient.close()
    }
}
