package core.network

import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.RequestTimeout
import java.net.ConnectException

internal object RequestCodeAnalyzer {
    fun getReason(code: HttpStatusCode): String {
        return when (code) {
            NotFound -> "${toDescription(code)}.Make sure the Url is valid"
            BadRequest -> "${toDescription(code)}: Server failed to process the request due to a client error. Probable causes may include Invalid Request Format, Invalid Parameters, Invalid Headers, URL Encoding Issues, Payload Too Large, Invalid Operations, Parsing JSON/XML Errors, Cookie/Header Size Exceeding Limits, or Security/Authentication Issues."
            RequestTimeout -> "${toDescription(code)}.Internet may not be available or the server may not be running"
            else -> toDescription(code)
        }
    }

    private fun toDescription(code: HttpStatusCode) = "$code: ${code.description}"
    fun getReason(exception: Throwable): String {
        return when (exception) {
            is ConnectException -> "Failed to connect to the server."
            is SocketTimeoutException -> "Connection timed out."
            is ClientRequestException -> "Client request error: ${exception.response.status.description}"
            is ServerResponseException -> "Server error: ${exception.response.status.description}"
            is RedirectResponseException -> "Unexpected redirect: ${exception.response.status.description}"
            else -> "Unknown error occurred: ${exception.message}"
        }
    }
}