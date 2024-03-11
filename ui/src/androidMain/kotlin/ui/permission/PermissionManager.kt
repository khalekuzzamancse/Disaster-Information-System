package ui.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionManager {
    val CHANGE_NETWORK_STATE =
        Permission("android.permission.CHANGE_NETWORK_STATE", "CHANGE_NETWORK_STATE")
    val INTERNET = Permission("android.permission.INTERNET", "INTERNET")
    val ACCESS_NETWORK_STATE =
        Permission("android.permission.ACCESS_NETWORK_STATE", "ACCESS_NETWORK_STATE")

    val internetPermission = listOf(CHANGE_NETWORK_STATE, INTERNET, ACCESS_NETWORK_STATE)

    // Notification permissions
    val FOREGROUND_SERVICE =
        Permission("android.permission.FOREGROUND_SERVICE", "FOREGROUND_SERVICE")
    val POST_NOTIFICATIONS =
        Permission("android.permission.POST_NOTIFICATIONS", "POST_NOTIFICATIONS")

    val notificationPermissions = listOf(
        FOREGROUND_SERVICE,
        POST_NOTIFICATIONS
    )

    // Google Maps permissions
    val ACCESS_COARSE_LOCATION =
        Permission("android.permission.ACCESS_COARSE_LOCATION", "ACCESS_COARSE_LOCATION")
    val ACCESS_BACKGROUND_LOCATION =
        Permission("android.permission.ACCESS_BACKGROUND_LOCATION", "ACCESS_BACKGROUND_LOCATION")
    val ACCESS_FINE_LOCATION =
        Permission("android.permission.ACCESS_FINE_LOCATION", "ACCESS_FINE_LOCATION")

    val googleMapsPermissions = listOf(
        ACCESS_COARSE_LOCATION,
        ACCESS_BACKGROUND_LOCATION,
        ACCESS_FINE_LOCATION
    )

    // Other permissions
    val READ_EXTERNAL_STORAGE =
        Permission("android.permission.READ_EXTERNAL_STORAGE", "READ_EXTERNAL_STORAGE")

    val storagePermission = listOf(
        READ_EXTERNAL_STORAGE
    )

    // Permissions with maxSdkVersion="32"
    val maxSdkVersion32Permissions = listOf(
        ACCESS_FINE_LOCATION,
        READ_EXTERNAL_STORAGE
    )

    fun hasPermissions(permissions: List<Permission>, context: Context): Boolean {
        var isGranted = true
        permissions.forEach { permission ->
            isGranted = isGranted && hasPermissions(permission, context)
        }
        return isGranted
    }

    fun hasPermissions(permission: Permission, context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission.androidName
        ) == PackageManager.PERMISSION_GRANTED
    }


}

