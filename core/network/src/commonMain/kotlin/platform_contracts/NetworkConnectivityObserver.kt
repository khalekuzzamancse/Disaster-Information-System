package platform_contracts

import kotlinx.coroutines.flow.StateFlow

// In your shared module

/**
 *
 * Using an interface instead of an 'expect' class provides several benefits:
 * - Providing separate  constructor defining for each implementation
 */
interface NetworkConnectivityObserver {
    val networkStatus: StateFlow<Boolean>

    /**
     * Checks if an internet connection is currently available.
     * @return Boolean indicating if the internet is available.
     */
    fun isInternetAvailable(): Boolean

    /**
     * Cleans up resources or listeners associated with this observer
     * to prevent memory leaks or other unwanted behavior.
     */
    fun unregisterNetworkCallback()
}
