package routes

import androidx.compose.runtime.Composable
import feature.home.ui.destination.HomeDestination

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