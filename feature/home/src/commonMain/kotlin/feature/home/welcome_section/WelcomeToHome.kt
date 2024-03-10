package feature.home.welcome_section

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import disasterinformationsystem.feature.home.generated.resources.Res
import disasterinformationsystem.feature.home.generated.resources.just_logo
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun WelcomeToHome(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val res: DrawableResource =Res.drawable.just_logo
        //after that compile it again to generate Res class, use : .\gradlew generateComposeResClass
        Image(
            modifier=Modifier.size(64.dp),
            painter = painterResource(res),//org.jetbrains.compose.resources.
            contentDescription = null,
        )
        Text(text = "Welcome to", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.width(16.dp))
        AppNameLogoSection(modifier = Modifier)

    }


}

