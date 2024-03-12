package ui.permission

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
/**
 *  @param shortName is for showing the useful message to user and for find bug easily
 */
data class Permission(
    val androidName: String,
    val shortName: String
)

object PermissionFactory {

    fun internetPermissions(): List<Permission> = listOf(
        Permission("android.permission.CHANGE_NETWORK_STATE", "Change Network State"),
        Permission("android.permission.INTERNET", "Internet"),
        Permission("android.permission.ACCESS_NETWORK_STATE", "Access Network State")
    )

    fun notificationPermissions(): List<Permission> = listOf(
        Permission("android.permission.FOREGROUND_SERVICE", "Foreground Service"),
        Permission("android.permission.POST_NOTIFICATIONS", "Post Notifications")
    )

    fun googleMapsPermissions(): List<Permission> = listOf(
        Permission("android.permission.ACCESS_COARSE_LOCATION", "Access Coarse Location"),
        Permission("android.permission.ACCESS_FINE_LOCATION", "Access Fine Location")
    )

    fun storagePermissions(): List<Permission> {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> listOf(
                Permission(READ_MEDIA_IMAGES, "Read Images"),
                Permission(READ_MEDIA_VIDEO, "Read Images"),
                Permission(READ_MEDIA_VISUAL_USER_SELECTED, "Visual User Selected")
            )

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> listOf(
                Permission(READ_MEDIA_IMAGES, "Read Images"),
                Permission(READ_MEDIA_VIDEO, "Read Images")
            )

            else -> listOf(
                Permission(READ_EXTERNAL_STORAGE, "Read External Storage")
            )
        }
    }

    fun hasPermissions(permissions: List<Permission>, context: Context): Boolean =
        permissions.all { permission ->
            ContextCompat.checkSelfPermission(context, permission.androidName) == PackageManager.PERMISSION_GRANTED
        }

    fun hasPermissions(permission: Permission, context: Context): Boolean =
        ContextCompat.checkSelfPermission(context, permission.androidName) == PackageManager.PERMISSION_GRANTED
}
