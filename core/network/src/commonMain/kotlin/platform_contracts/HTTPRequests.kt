@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package platform_contracts

import core.network.components.Header
import core.network.components.NetworkFileType

expect object HTTPRequests {
    suspend fun uploadFile(url: String, fileType: NetworkFileType, byteArray: ByteArray): Result<String>
    suspend inline fun <reified T> get(networkMonitor: NetworkConnectivityObserver, url: String): Result<T>
    suspend inline fun <reified T> get(url: String, header: Header): Result<T>
}



