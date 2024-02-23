package com.khalekuzzaman.just.cse.datacollect.chat_ui


import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow



@Composable
fun MessageInputField(
    modifier: Modifier = Modifier,
) {
    var message by remember {
        mutableStateOf("")
    }
    val emptyMessage = message.isEmpty()
    TextField(
        value = message,
        onValueChange = {message=it},
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainerLow,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainerLow,

            ),
        modifier = modifier.border(
            width = 2.dp, color = MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(12.dp)
        ),
        textStyle = MaterialTheme.typography.bodyLarge


    )


}