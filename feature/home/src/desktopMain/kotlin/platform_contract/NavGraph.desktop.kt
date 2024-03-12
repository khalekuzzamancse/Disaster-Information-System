package platform_contract

import androidx.compose.runtime.Composable

@Composable
actual fun HomeModuleNavGraph(
    enableSendButton: Boolean,
    onSendRequest: () -> Unit,
    onExitRequest: () -> Unit,
) {
}