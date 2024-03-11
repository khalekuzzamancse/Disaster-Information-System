package platform_contract

import androidx.compose.runtime.Composable
import feature.home.ui.destination.HomeDestinationCommon
import ui.SnackBarMessage
import ui.permission.PermissionDecorator
import ui.permission.PermissionManager

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
    PermissionDecorator(
        permissions = PermissionManager.notificationPermissions
    ){
        HomeDestinationCommon(
            snackBarMessage = snackBarMessage,
            isSending = isSending,
            onSendRequest = onSendRequest,
            onAboutUsRequest = onAboutUsRequest
        )
    }

}