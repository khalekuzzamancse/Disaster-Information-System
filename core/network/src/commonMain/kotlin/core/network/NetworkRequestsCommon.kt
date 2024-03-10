package core.network

import core.network.components.Header
import core.network.components.NetworkFileType
import core.network.post.KtorFilePost
import core.network.get.KtorGetRequests
import platform_contracts.NetworkConnectivityObserver


/**
 * * Client module should not depends on this the  client module should depends on *NetworkRequests*
 *
 * * The wrapper of underlying network library
 * * Used to prevent the androidMain,DesktopMain and iOS main to access the underlying library
 * * As a result the client module need to access the underlying library
 * * So using to decouple the client module to depends on specific underlying library
 */
@PublishedApi
internal object NetworkRequestsCommon {
    suspend fun uploadFile(url: String, fileType: NetworkFileType, byteArray: ByteArray,networkMonitor: NetworkConnectivityObserver):Result<String>{
        return  KtorFilePost().upload(url, fileType, byteArray,networkMonitor)
    }
    suspend inline fun < reified T> requestForGet(networkMonitor: NetworkConnectivityObserver, url: String):Result<T>{
       return KtorGetRequests().request<T>(networkMonitor, url)
    }
    suspend inline fun <reified T> requestForGet(url: String, header: Header): Result<T>{
        return KtorGetRequests().request<T>(url, header)
    }

}