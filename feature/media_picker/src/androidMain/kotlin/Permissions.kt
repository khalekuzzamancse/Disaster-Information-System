import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

/*
Handling the permission without any
library so that for just checking permission,we need not to
import the whole library
 */

@Composable
fun PermissionChecker(permissions: List<String>) {
    val context = LocalContext.current
    var isPermissionGranted by remember {
        mutableStateOf(
            isAlreadyAllGranted(
                permissions,
                context
            )
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { map: Map<String, Boolean> ->
            isPermissionGranted = map.all { it.value }
        })
    LaunchedEffect(isPermissionGranted) {
        println("PermissionTesting:$isPermissionGranted")
        if (!isPermissionGranted)
            launcher.launch(permissions.toTypedArray<String>())
    }

}

private fun isAlreadyAllGranted(permissions: List<String>, context: Context): Boolean {
    var isGranted = true
    permissions.forEach { permission ->
        isGranted = isGranted && ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
    return isGranted
}