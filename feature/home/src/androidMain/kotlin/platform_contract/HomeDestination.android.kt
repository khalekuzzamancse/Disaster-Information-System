package platform_contract

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import feature.home.ui.destination.HomeDestinationCommon
import ui.SnackBarMessage
import ui.permission.PermissionDecorator
import ui.permission.PermissionFactory

/**
 * Needed because we have to do some platform specific such as permission manage
 */
@Composable
actual fun HomeDestination(
    snackBarMessage: SnackBarMessage?,
    isSending: Boolean,
    onSendRequest: () -> Unit,
    onAboutUsRequest: () -> Unit
) {

    /**
     * Check permission only when sent is clicked
     */
    var isSentButtonClicked by remember { mutableStateOf(false) }
    if (isSentButtonClicked) {
        PermissionDecorator(
            permissions = PermissionFactory.notificationPermissions()
        ) {
            HomeDestinationCommon(
                snackBarMessage = snackBarMessage,
                isSending = isSending,
                onSendRequest = {
                    isSentButtonClicked = true
                    onSendRequest()
                },
                onAboutUsRequest = onAboutUsRequest
            )
        }

    } else {
        HomeDestinationCommon(
            snackBarMessage = snackBarMessage,
            isSending = isSending,
            onSendRequest = {
                isSentButtonClicked = true
                onSendRequest()
            },
            onAboutUsRequest = onAboutUsRequest
        )
    }

}