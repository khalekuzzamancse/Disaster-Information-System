package core.network


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json


object NetworkGetRequests{
    suspend inline fun <reified T> request(networkMonitor: NetworkMonitor,url: String): Result<T> {
        val httpClient = HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val notConnected = !networkMonitor.isNetworkAvailable()
        if (notConnected) {
            return Result.failure(Throwable("Internet not connected"))
        }
        return try {
            val response = httpClient.get(url) {}.body<T>()
            Result.success(response)

        } catch (ex: Exception) {
            Result.failure(Throwable(ex))
        } finally {
            try {
                httpClient.close()
            } catch (_: Exception) {
            }
        }
    }
}
