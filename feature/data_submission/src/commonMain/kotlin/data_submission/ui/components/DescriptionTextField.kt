package data_submission.ui.components
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp

@Composable
internal fun DescriptionTextField(
    modifier: Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    shape: Shape = TextFieldDefaults.shape,
    errorMessage: String? = null,
    label: String?=null,
    value: String,
    leadingIcon: ImageVector?,
    onValueChanged: (String) -> Unit,
) {
    val singleLine=false
    val colors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
    )
    val content: @Composable ColumnScope.() -> Unit = if (errorMessage == null) @Composable {
        {
            Row {
                if (leadingIcon != null) {
                    Icon(imageVector = leadingIcon, contentDescription = null)
                }
                Spacer(Modifier.width(4.dp))
                if (label != null) {
                    Text(text = label)
                }
            }
            TextField(
                singleLine = singleLine,
                label = null,
                shape = shape,
                modifier = modifier,
                value = value,
                onValueChange = onValueChanged,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
                colors = colors,
            )

        }
    } else @Composable {
        {
            Row {
                if (leadingIcon != null) {
                    Icon(imageVector = leadingIcon, contentDescription = null)
                }
                if (label != null) {
                    Text(text = label)
                }
            }
            TextField(
                label = null,
                shape = shape,
                modifier = modifier,
                value = value,
                onValueChange = onValueChanged,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
                colors = colors,
                isError = true,
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