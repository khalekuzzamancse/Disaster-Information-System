package core

import android.net.ConnectivityManager
import android.net.NetworkCapabilities


//Annotation is using to warning of missing permission
/**
 * @param connectivityManager instead of Context,taking the [ConnectivityManager]
 * because Context is UI dependent and this module does not depends on UI
 */
@Suppress("MissingPermission")
class AndroidNetworkConnectivityObserver(
    private val connectivityManager: ConnectivityManager
)  {


     fun isInternetAvailable(): Boolean = checkNetworkConnectivity()

    private fun checkNetworkConnectivity(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

}
