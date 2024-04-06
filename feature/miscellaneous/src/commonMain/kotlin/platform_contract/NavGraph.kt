package platform_contract

import androidx.compose.runtime.Composable

@Composable
expect fun HomeModuleNavGraph(
    enableSendButton: Boolean,
    onSendRequest: () -> Unit,
    onExitRequest: () -> Unit,
)