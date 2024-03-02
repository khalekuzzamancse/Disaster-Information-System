package com.khalekuzzaman.just.cse.datacollect.network

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod

suspend fun uploadVideoByteArray(url: String, byteArray: ByteArray?): Result<String> {
    val httpClient = HttpClient()

    return try {
        val response = if (byteArray != null) {
            httpClient.submitFormWithBinaryData(
                url = url,
                formData = formData {
                    append("video", byteArray, Headers.build {
                        append(HttpHeaders.ContentType, "video/mp4") // Change to your video's MIME type
                        append(HttpHeaders.ContentDisposition, "filename=video.mp4") // Change to your video's file name
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
