@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package platform_contracts

import core.network.Header
import core.network.NetworkFileType
import core.network.NetworkMonitor
import core.network.NetworkRequestsInternal

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
/**
 * Separator between the underlying implementation ,to make loosely couple
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