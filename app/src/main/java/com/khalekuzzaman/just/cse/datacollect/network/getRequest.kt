package com.khalekuzzaman.just.cse.datacollect.network


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable

@Serializable
data class Bank(
    val account: String,
    val trust: Double,
    val fee: Int
)
suspend inline fun <reified T> getRequest(url: String):Result<T>{
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }
    return try {
        val response = httpClient.get(url){
        }.body<T>()
        println("APIResponse:$response")
        Result.success(response)
    } catch (ex: Exception) {
        Result.failure(Throwable(ex))
    }
}

