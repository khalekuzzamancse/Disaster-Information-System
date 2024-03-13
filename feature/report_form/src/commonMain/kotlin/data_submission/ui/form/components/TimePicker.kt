package data_submission.ui.form.components

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerCustom(
    modifier: Modifier,
    label: String,
    value: TimePickerData,
    leadingIcon: ImageVector?,
    trailingIcon: ImageVector? = null,
    colors:TextFieldColors=TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
    ),
    onTimeSelected: (TimePickerData) -> Unit,
) {

    val timeState = rememberTimePickerState(11, 30, false)
    val state= TimePickerData(timeState.hour,timeState.minute)


    var openDialog by remember { mutableStateOf(false) }
    ReadOnlyTextField(
        modifier = modifier,
        label = label,
        value = "$value",
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        onTrailingIconClick = { openDialog = true },
        colors=colors
    )

    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onTimeSelected(state)
                        openDialog=false
                    },
                    enabled = "$state".isNotEmpty()
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
                TimePicker(state = timeState)
        }
    }
}

data class TimePickerData(
    val hour: Int,
    val minute: Int
) {
    override fun toString(): String {
        val hourIn12Format = if (hour == 0 || hour == 12) 12 else hour % 12
        val amPm = if (hour < 12) "AM" else "PM"
        val formattedHour = hourIn12Format.toString().padStart(2, '0')
        val formattedMinute = minute.toString().padStart(2, '0')
        return "$formattedHour:$formattedMinute $amPm"
    }
    fun isValid(): Boolean {
        return hour in 0..23 && minute in 0..59
    }
}
