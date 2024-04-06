package feature.home.ui.destination

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import feature.home.ui.destination.AboutUs
import feature.home.ui.destination.ContactUs

@Composable
fun AboutUsContactUs(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "About Us",
            style = MaterialTheme.typography.headlineMedium,
        )
        HorizontalDivider(Modifier.fillMaxWidth())
        AboutUs()
        Text(
            text = "Contact Us",
            style = MaterialTheme.typography.headlineMedium,
        )
        HorizontalDivider(Modifier.fillMaxWidth())
        ContactUs()
    }

}