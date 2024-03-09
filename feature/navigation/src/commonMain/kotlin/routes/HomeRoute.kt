package routes

import androidx.compose.runtime.Composable
import feature.home.HomeDestination

@Composable
fun HomeRoute(
    onSend:()->Unit,
    isSending:Boolean=false,
) {
    HomeDestination(
        isSending = isSending,
        onSend = onSend
    )

}