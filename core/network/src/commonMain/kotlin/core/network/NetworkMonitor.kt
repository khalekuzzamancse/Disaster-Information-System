package core.network

fun interface NetworkMonitor {
   fun isNetworkAvailable():Boolean
}