package platform_contracts

import core.network.components.Header
import core.network.components.NetworkFileType
import core.network.NetworkRequestsCommon

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
/**
 * Separator between the underlying implementation ,to make loosely couple.
 * though right now it delegate to commonMain,so it seems that why this access point,because we can
 * direcly used the [NetworkRequestsCommon] in client module.
 * This is made so that in future we might be change the underlying network library for a specific platform
 * that is why we made this,so that remove or replacing the underlying library will not cause the the client code crash or changed
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
        byteArray: ByteArray
    ): Result<String> {
       return NetworkRequestsCommon.uploadFile(url, fileType, byteArray)
    }
}