package platform_contracts

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


//Annotation is using to warning of missing permission
/**
 * @param connectivityManager instead of Context,taking the [ConnectivityManager]
 * because Context is UI dependent and this module does not depends on UI
 */
@Suppress("MissingPermission")
class AndroidNetworkConnectivityObserver(
    private val connectivityManager: ConnectivityManager
) : NetworkConnectivityObserver {
    private val _networkStatus = MutableStateFlow(false)
    override val networkStatus: StateFlow<Boolean> = _networkStatus

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _networkStatus.value = true
        }

        override fun onLost(network: Network) {
            _networkStatus.value = false
        }
    }

    init {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        _networkStatus.value = checkNetworkConnectivity()
    }

    override fun isInternetAvailable(): Boolean = checkNetworkConnectivity()

    private fun checkNetworkConnectivity(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    override fun unregisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
