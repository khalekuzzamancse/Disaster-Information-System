package core.network.get


import core.network.components.Header
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import platform_contracts.NetworkConnectivityObserver


/**
Because of Generic ,we are using inline function,to keep the inline function short as possible
using the class.
Because of the inline reified we are not able to make this completely private.
later try to refactor is as as private.
 Right now we are making it internal,
 we are trying to hide it from IDE suggestion by it:
* * @PublishedApi internal is the intended way of exposing non-public API for use in public inline functions.
 * * Try out for better solution if any...
 */

@PublishedApi
internal class KtorGetRequests {
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend inline fun <reified T> request(networkMonitor: NetworkConnectivityObserver, url: String): Result<T> {
        val notConnected = !networkMonitor.isInternetAvailable()
        if (notConnected) {
            return Result.failure(Throwable("No Internet Connection !"))
        }
        return try {
            val response: T = httpClient.get(url).body<T>()
            Result.success(response)
        } catch (ex: Exception) {
            Result.failure(Throwable(ex))
        } finally {
            closeConnection()
        }
    }

    suspend inline fun <reified T> request(url: String, header: Header): Result<T> {
        val httpClient = HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
        return try {
            val response = httpClient.get(url){
                header(key=header.key, value=header.value)
            }.body<T>()
            Result.success(response)
        } catch (ex: Exception) {
            Result.failure(Throwable("Failed e:${ex.message}"))
        }
    }
    @PublishedApi
    internal fun closeConnection() {
        try {
            httpClient.close()
        } catch (_: Exception) {
        }
    }


}

