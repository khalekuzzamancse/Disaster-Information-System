package platform_contracts

import core.network.Header
import core.network.NetworkFileType
import core.network.NetworkMonitor
import core.network.NetworkRequestsInternal

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
/**
 * Separator between the underlying implementation ,to make loosely couple.
 * though right now it delegate to commonMain,so it seems that why this access point,because we can
 * direcly used the [NetworkRequestsInternal] in client module.
 * This is made so that in future we might be change the underlying network library for a specific platform
 * that is why we made this,so that remove or replacing the underlying library will not cause the the client code crash or changed
 */
actual object NetworkRequests {
    actual suspend inline fun <reified T> get(
        networkMonitor: NetworkMonitor,
        url: String
    ): Result<T> {
        return NetworkRequestsInternal.request<T>(networkMonitor = networkMonitor, url = url)
    }

    actual suspend inline fun <reified T> get(
        url: String,
        header: Header
    ): Result<T> {
        return NetworkRequestsInternal.request<T>(url = url, header = header)

    }

    actual suspend fun uploadFile(
        url: String,
        fileType: NetworkFileType,
        byteArray: ByteArray
    ): Result<String> {
       return NetworkRequestsInternal.uploadFile(url, fileType, byteArray)
    }
}