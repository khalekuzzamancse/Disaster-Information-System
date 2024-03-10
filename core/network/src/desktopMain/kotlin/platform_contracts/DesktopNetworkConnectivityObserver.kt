package platform_contracts

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okio.IOException
import java.net.HttpURLConnection
import java.net.URL

// In your desktop-specific source set

class DesktopConnectivityObserver : NetworkConnectivityObserver {
    private val _networkStatus = MutableStateFlow(false)
    override val networkStatus: StateFlow<Boolean> = _networkStatus

    init {
        _networkStatus.value = isInternetAvailable()
    }

    override fun isInternetAvailable(): Boolean {
        return try {
            val url = URL("http://www.google.com")
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("User-Agent", "ConnectivityTest")
            connection.setRequestProperty("Connection", "close")
            connection.connectTimeout = 1000 // Timeout for connecting
            connection.connect()
            connection.responseCode == 200
        } catch (e: IOException) {
            false
        }
    }

    override fun unregisterNetworkCallback() {
        // For desktop, there might not be an active listener to unregister,
        // but you can implement cleanup of resources if necessary.
    }
}
