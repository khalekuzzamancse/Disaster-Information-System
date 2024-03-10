package data_submission.ui.components
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector

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

    Column(modifier = modifier) {
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
                    Icon(imageVector = leadingIcon, contentDescription = null)
                }
            },
            trailingIcon = {
                if (trailingIcon != null) {
                    Icon(
                        modifier = Modifier.clickable { onTrailingIconClick?.invoke() },
                        imageVector = trailingIcon,
                        contentDescription = null
                    )
                }
            },
            colors = colors
        )
    }


}