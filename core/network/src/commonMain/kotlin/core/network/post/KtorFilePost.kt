package core.network.post

import core.network.NetworkFileType
import io.ktor.client.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.http.content.PartData


internal class KtorFilePost {
    private val httpClient = HttpClient()

    suspend fun upload(
        url: String,
        networkFileType: NetworkFileType,
        byteArray: ByteArray
    ): Result<String> {
        return try {
            val response = submit(url, networkFileType = networkFileType, byteArray = byteArray)
            Result.success(response)
        } catch (ex: Exception) {
            Result.failure(Throwable("Failed to upload due to: ${ex.javaClass.simpleName}"))
        } finally {
            close()
        }
    }

    private suspend fun submit(
        url: String,
        networkFileType: NetworkFileType,
        byteArray: ByteArray
    ): String {
        return httpClient.submitFormWithBinaryData(
            url = url,
            formData = createFormData(networkFileType = networkFileType, byteArray = byteArray)
        ) {
            method = HttpMethod.Post
        }.bodyAsText()
    }

    private fun createFormData(
        networkFileType: NetworkFileType,
        byteArray: ByteArray
    ): List<PartData> {
        return formData {
            append(key = networkFileType.key, value = byteArray, headers = Headers.build {
                append(
                    name = HttpHeaders.ContentType,
                    value = networkFileType.contentType
                ) // Adjust based on actual image type
                append(HttpHeaders.ContentDisposition, "filename=${networkFileType.key}")
            })
        }
    }

    private fun close() {
        httpClient.close()
    }
}

