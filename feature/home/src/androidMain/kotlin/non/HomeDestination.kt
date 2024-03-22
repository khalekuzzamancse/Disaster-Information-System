package non

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import feature.home.ui.destination.HomeDestination
import ui.SnackBarMessage
import ui.permission.PermissionDecorator
import ui.permission.PermissionFactory

/**
 * Needed because we have to do some platform specific such as permission manage
 */
@Composable
internal fun HomeDestinationAndroid(
    snackBarMessage: SnackBarMessage?,
    enableSend: Boolean,
    onSendRequest: () -> Unit,
    onContactUsRequest:()->Unit,
    onAboutUsRequest: () -> Unit,
) {

    /**
     * Check permission only when sent is clicked
     */
    var isSentButtonClicked by remember { mutableStateOf(false) }
    if (isSentButtonClicked) {
        PermissionDecorator(
            permissions = PermissionFactory.notificationPermissions()
        ) {
            HomeDestination(
                snackBarMessage = snackBarMessage,
                enableSend = enableSend,
                onSendRequest = {
                    isSentButtonClicked = true
                    onSendRequest()
                },
                onAboutUsRequest = onAboutUsRequest,
                onContactUsRequest=onContactUsRequest
            )
        }

    } else {
        HomeDestination(
            snackBarMessage = snackBarMessage,
            enableSend = enableSend,
            onSendRequest = {
                isSentButtonClicked = true
                onSendRequest()
            },
            onAboutUsRequest = onAboutUsRequest,
            onContactUsRequest=onContactUsRequest
        )
    }

}