package platform_contract

import androidx.compose.runtime.Composable
import ui.SnackBarMessage

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
}