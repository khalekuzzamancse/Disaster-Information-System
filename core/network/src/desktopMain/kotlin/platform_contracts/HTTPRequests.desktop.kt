@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package platform_contracts

import core.network.components.Header
import core.network.components.NetworkFileType
import core.network.NetworkRequestsCommon

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
/**
 * Separator between the underlying implementation ,to make loosely couple
 */
actual object HTTPRequests {
    actual suspend inline fun <reified T> get(
        networkMonitor: NetworkConnectivityObserver,
        url: String
    ): Result<T> {
        return NetworkRequestsCommon.requestForGet<T>(networkMonitor = networkMonitor, url = url)
    }

    actual suspend inline fun <reified T> get(
        url: String,
        header: Header
    ): Result<T> {
        return NetworkRequestsCommon.requestForGet<T>(url = url, header = header)

    }

    actual suspend fun uploadFile(
        url: String,
        fileType: NetworkFileType,
        byteArray: ByteArray,
        networkMonitor: NetworkConnectivityObserver,
    ): Result<String> {
        return NetworkRequestsCommon.uploadFile(url, fileType, byteArray,networkMonitor)
    }
}