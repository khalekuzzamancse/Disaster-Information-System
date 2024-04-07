package feature.home.ui.destination

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics

@Composable
fun AboutUsContactUs(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        _AboutUsSection(Modifier.semantics(mergeDescendants = true) {
            //title will be used to talkback
        })
        _ContactUsSection(Modifier.semantics(mergeDescendants = true) {
            //title will be used to talkback
        })

    }

}

@Composable
private fun _AboutUsSection(
    modifier: Modifier
) {
    Column(modifier) {
        Text(
            text = "About Us",
            style = MaterialTheme.typography.headlineMedium,
        )
        HorizontalDivider(Modifier.fillMaxWidth())
        AboutUs(
            modifier = Modifier
        )
    }

}

@Composable
private fun _ContactUsSection(
    modifier: Modifier=Modifier,
) {
    Column(modifier) {
        Text(
            text = "Contact Us",
            style = MaterialTheme.typography.headlineMedium,
        )
        HorizontalDivider(Modifier.fillMaxWidth())
        ContactUs()
    }

}