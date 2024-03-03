package com.khalekuzzaman.just.cse.datacollect.network

import io.ktor.client.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.http.content.PartData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun uploadImageByteArray(url: String, byteArray: ByteArray): Result<String> {
    val httpClient = HttpClient()

    return try {
        val response = httpClient.submitFormWithBinaryData(
            url = url,
            formData = formData {
                append("image", byteArray, Headers.build {
                    append(HttpHeaders.ContentType, "image/jpeg")
                    append(HttpHeaders.ContentDisposition, "filename=image.jpg")
                })
            }
        ) {
            method = HttpMethod.Post
        }

        Result.success(response.bodyAsText())
    } catch (ex: Exception) {
        Result.failure(Throwable("Failed to upload due to :${ex.javaClass.simpleName}"))
    } finally {
        httpClient.close()
    }
}

class FileUploader {
    private val httpClient = HttpClient()

    suspend fun uploadImageByteArray(url: String, byteArray: ByteArray): Result<String> = withContext(
        Dispatchers.IO) {
        try {
            val response = submitImage(url, byteArray)
            Result.success(response)
        } catch (ex: Exception) {
            Result.failure(Throwable("Failed to upload due to: ${ex.javaClass.simpleName}"))
        }
        finally {
            close()
        }
    }

    private suspend fun submitImage(url: String, byteArray: ByteArray): String {
        return httpClient.submitFormWithBinaryData(
            url = url,
            formData = createFormData("image", byteArray, "image/jpeg", "image.jpg")
        ) {
            method = HttpMethod.Post
        }.bodyAsText()
    }

    private fun createFormData(name: String, byteArray: ByteArray, contentType: String, fileName: String): List<PartData> {
        return formData {
            append(
                key = name,
                value = byteArray,
                headers = Headers.build {
                    append(HttpHeaders.ContentType, contentType)
                    append(HttpHeaders.ContentDisposition, "filename=$fileName")
                }
            )
        }
    }

    private fun close() {
        httpClient.close()
    }
}

