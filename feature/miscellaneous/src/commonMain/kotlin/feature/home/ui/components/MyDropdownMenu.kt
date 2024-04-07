package feature.home.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
internal fun MyDropDownMenu(
    modifier: Modifier,
    onAboutClick: () -> Unit = {},
    onContactUsClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,//merged with parent
                tint = MaterialTheme.colorScheme.primary //to indicate as clickable

            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            _AboutUsButton(
                modifier = Modifier
                    .semantics(mergeDescendants = true) {
                        contentDescription = "navigate button to go"
                    },
                onClick = onAboutClick
            )
            _ContactUsButton(
                modifier = Modifier
                    .semantics(mergeDescendants = true) {
                        contentDescription = "navigate button to go"
                    },
                onClick = onContactUsClick
            )


        }
    }
}

@Composable
private fun _AboutUsButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    DropdownMenuItem(
        modifier = modifier,
        text = { Text("About Us") },
        onClick = onClick,
        leadingIcon = {
            Icon(
                Icons.Default.Person,
                contentDescription = null,//merged with parent
                tint = MaterialTheme.colorScheme.tertiary
            )
        }

    )
}

@Composable
private fun _ContactUsButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    DropdownMenuItem(
        modifier =modifier,
        text = { Text("Contact Us") },
        onClick = onClick,
        leadingIcon = {
            Icon(
                Icons.Default.Info,
                contentDescription = null,//merged with parent
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    )
}
