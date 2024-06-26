package report_form.ui.form.components
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
internal fun ReadOnlyTextField(
    modifier: Modifier,
    label: String,
    value: String,
    leadingIcon: ImageVector?,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    shape: Shape = TextFieldDefaults.shape,
    colors:TextFieldColors,
) {

    Column(modifier = modifier.semantics { contentDescription="Read only Text Field" }) {
        Text(text = label)
        TextField(
            label = null,
            shape = shape,
            modifier = modifier,
            value = value,
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
                if (leadingIcon != null) {
                    Icon(imageVector = leadingIcon, contentDescription = null,tint = MaterialTheme.colorScheme.tertiary)
                }
            },
            trailingIcon = {
                if (trailingIcon != null) {
                    Icon(
                        modifier = Modifier.clickable { onTrailingIconClick?.invoke() },
                        imageVector = trailingIcon,
                        contentDescription = "Leading Icon",
                        tint = MaterialTheme.colorScheme.primary//to indicate that it is important/clickable
                    )
                }
            },
            colors = colors
        )
    }


}