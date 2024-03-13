package report_form.ui.form.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun FormTextField(
    modifier: Modifier,
    label: String?=null,
    value: String,
    leadingIcon: ImageVector?,
    keyboardType: KeyboardType= KeyboardType.Text,
    shape: Shape = TextFieldDefaults.shape,
    singleLine:Boolean=true,
    errorMessage: String? = null,
    onValueChanged: (String) -> Unit,
) {
    val colors=TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
    )
    val content: @Composable ColumnScope.() -> Unit = if (errorMessage == null) @Composable {
        {
            if (label != null) {
                Text(text = label)
            }
            TextField(
                label=null,
                shape = shape,
                modifier = modifier,
                value = value,
                onValueChange = onValueChanged,
                leadingIcon = {
                    if (leadingIcon != null) {
                        Icon(imageVector = leadingIcon, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
                colors = colors,
                singleLine=singleLine
            )

        }
    } else @Composable {
        {
            if (label != null) {
                Text(text = label)
            }
            TextField(
                label=null,
                shape = shape,
                modifier = modifier,
                value = value,
                onValueChange = onValueChanged,
                leadingIcon = {
                    if (leadingIcon != null) {
                        Icon(imageVector = leadingIcon, contentDescription = null)
                    }
                },

                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
                colors =colors,
                isError = true,
                singleLine=singleLine,
                supportingText = {
                    Text(
                        text = errorMessage
                    )
                },

            )
        }

    }
    Column(modifier = modifier) {
        content()
    }


}