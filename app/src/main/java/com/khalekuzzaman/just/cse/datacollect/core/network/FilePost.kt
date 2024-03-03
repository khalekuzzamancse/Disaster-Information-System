package com.khalekuzzaman.just.cse.datacollect.core.network

import io.ktor.client.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.http.content.PartData

interface FilePoster {

    class FilePost(private val url: String) {
        private val httpClient = HttpClient()

        suspend fun upload(fileType: FileType, byteArray: ByteArray): Result<String> {
            return try {
                val response = submit(fileType = fileType, byteArray = byteArray)
                Result.success(response)
            } catch (ex: Exception) {
                Result.failure(Throwable("Failed to upload due to: ${ex.javaClass.simpleName}"))
            } finally {
                close()
            }
        }

        private suspend fun submit(fileType: FileType, byteArray: ByteArray): String {
            return httpClient.submitFormWithBinaryData(
                url = url,
                formData = createFormData(fileType = fileType, byteArray = byteArray)
            ) {
                method = HttpMethod.Post
            }.bodyAsText()
        }

        private fun createFormData(fileType: FileType, byteArray: ByteArray): List<PartData> {
            return formData {
                append(key = fileType.key, value = byteArray, headers = Headers.build {
                    append(
                        name = HttpHeaders.ContentType,
                        value = fileType.contentType
                    ) // Adjust based on actual image type
                    append(HttpHeaders.ContentDisposition, "filename=image.jpg")
                })
            }
        }

        private fun close() {
            httpClient.close()
        }
    }

    /**
     * @param key is the key or file type that used  in the back-end while defining the end-point/api/rest controller
     * @param contentType used to the Client to make the request body/header
     */
    enum class FileType(val contentType: String, val key: String) {
        VIDEO(contentType = "video/mp4", key = "video"),
        IMAGE(contentType = "image/jpeg", key = "image");
    }

}
