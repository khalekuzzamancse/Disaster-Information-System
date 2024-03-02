package com.khalekuzzaman.just.cse.datacollect.network

import io.ktor.client.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*

suspend fun uploadImageByteArray(url: String, byteArray: ByteArray?): Result<String> {
    val httpClient = HttpClient()

    return try {
        val response = if (byteArray != null) {
            httpClient.submitFormWithBinaryData(
                url = url,
                formData = formData {
                    append("image", byteArray, Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg") // Adjust based on actual image type
                        append(HttpHeaders.ContentDisposition, "filename=image.jpg")
                    })
                }
            ) {
                method = HttpMethod.Post
            }
        } else {
            throw IllegalArgumentException("Byte array must not be null")
        }

        Result.success(response.bodyAsText())
    } catch (ex: Exception) {
        Result.failure(ex)
    } finally {
        httpClient.close()
    }
}
