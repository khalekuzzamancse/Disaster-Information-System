package ui.permission


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/*
Handling the permission without any
library so that for just checking permission,we need not to
import the whole library

 * Do not define explicit list,
 * because it might possible that for some the dialogue is not launched
 */

/**
 * It is safer and it keep no extra component,
 * it directly call the UI,if the permission has granted
 *
 */
@Composable
fun PermissionDecorator(
    permissions: List<Permission>,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    var isPermissionGranted by remember {
        mutableStateOf(
            PermissionFactory.hasPermissions(
                permissions,
                context
            )
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { map: Map<String, Boolean> ->
            //note that,it is possible that all permission is not granted because for some reason
            //some permission dialog is not launched,
            //so check it manually
            isPermissionGranted = map.all { it.value }
        })

    if (isPermissionGranted) {
        content()
    } else {
        PermissionHandler(permissions, onLaunchRequest = {
            launcher.launch(permissions.map { it.androidName }.toTypedArray<String>())
        })
    }


}

@Composable
private fun PermissionHandler(
    permissions: List<Permission>,
    onLaunchRequest: () -> Unit,
) {
    val context = LocalContext.current
    Box(
        Modifier
            .padding(8.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Following Permission are not Granted :")
            permissions.forEach { permission ->
                val hasNotGranted = !PermissionFactory.hasPermissions(permission, context)
                if (hasNotGranted) {
                    Text(permission.shortName)
                }
            }
            Button(onClick = onLaunchRequest) {
                Text("Grant Permission")
            }
        }

    }

}


