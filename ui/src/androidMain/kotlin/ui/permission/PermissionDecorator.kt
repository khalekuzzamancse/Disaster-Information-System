package ui.permission


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

    if(isPermissionGranted){
        //not wrapping the content() to something else to avoid hidden bugs
        //if we wrap then we are modifying the client ui which can causes side effect
        content()
    }

    AnimatedVisibility(
        visible = !isPermissionGranted,
        exit = slideOutVertically()
    ) {
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
    Box(
        Modifier
            .padding(8.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title(modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(Modifier.height(8.dp))
            NeededPermission(Modifier.align(Alignment.CenterHorizontally), permissions)
            Spacer(Modifier.height(16.dp))
            GrantButton(Modifier.align(Alignment.CenterHorizontally), onLaunchRequest)

        }

    }

}

@Composable
private fun GrantButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(modifier = modifier, onClick = onClick) {
        Icon(Icons.Default.Done, null)
        Spacer(Modifier.width(8.dp))
        Text("Grant")
    }
}

@Composable
private fun Title(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "Permissions Required to Continue",
        style = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
private fun NeededPermission(modifier: Modifier = Modifier, permissions: List<Permission>) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        permissions.forEach { permission ->
            val hasNotGranted = !PermissionFactory.hasPermissions(permission, context)
            if (hasNotGranted) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary)

                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = permission.shortName,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }

}
