package data_submission.ui.form.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
    textFieldModifier: Modifier=Modifier,
    shape: Shape = TextFieldDefaults.shape,
    label: String,
    value: String,
    leadingIcon: ImageVector,
    onValueChanged: (String) -> Unit,
) {
    val colors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
    )
    Column(modifier = modifier) {
        _Label(
            label = label,
            leadingIcon = leadingIcon
        )
        TextField(
            singleLine = false,
            label = null,
            shape = shape,
            modifier = textFieldModifier,
            value = value,
            onValueChange = onValueChanged,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            colors = colors,
        )
    }


}


@Composable
private fun _Label(modifier: Modifier = Modifier, label: String, leadingIcon: ImageVector) {
    Column(modifier = modifier) {
        Text(text = label)
        Spacer(Modifier.height(8.dp))
        Row {
            Spacer(Modifier.width(10.dp))
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }

}