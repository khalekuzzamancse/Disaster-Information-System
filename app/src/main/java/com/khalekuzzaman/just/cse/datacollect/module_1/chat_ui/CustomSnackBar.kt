package com.khalekuzzaman.just.cse.datacollect.module_1.chat_ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

enum class SnackBarMessageType{
    Error,Success
}
data class SnackBarMessage(val message: String, val type:SnackBarMessageType)
@Composable
fun CustomSnackBar(
   message: SnackBarMessage,
) {
    var show by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(Unit) {
        delay(4000)
        show = false
    }
    val containerColor = if (message.type ==SnackBarMessageType.Error) Color.Red else Color.Green
    val textColor = if (message.type ==SnackBarMessageType.Error) Color.White else Color.Black
    if (show) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            containerColor = containerColor,
            contentColor = textColor,
        ) {
            Text(message.message)
        }
    }


}
