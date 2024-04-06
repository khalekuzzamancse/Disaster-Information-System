package feature.home.ui.destination

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun ContactUs(
    modifier: Modifier=Modifier
) {
    Column(modifier =modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Icon(
                Icons.Default.School,
                "web icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Department of CSE",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 20.sp,
                modifier = Modifier
            )
        }
        Spacer(Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Icon(
                Icons.Default.Apartment,
                "web icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Jashore University of Science and Technology",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 16.sp,
                modifier = Modifier
            )
        }
        Spacer(Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Icon(
                Icons.Default.LocationOn,
                "web icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Jashore 7400, Bangladesh",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 16.sp,
                modifier = Modifier
            )
        }
        Spacer(Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Icon(
                Icons.Default.Link,
                "web icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "https://cse.just.edu.bd",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
            )

        }

    }
}