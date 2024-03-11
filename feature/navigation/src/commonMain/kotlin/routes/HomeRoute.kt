package routes

import androidx.compose.runtime.Composable
import platform_contract.HomeDestination

@Composable
fun HomeRoute(
    onSend:()->Unit,
    isSending:Boolean=false,
) {
    HomeDestination(
        isSending = isSending,
        onSendRequest = onSend
    )

}