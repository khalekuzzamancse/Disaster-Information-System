package feature.home.ui.welcome_section

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import disasterinformationsystem.feature.home.generated.resources.Res
import disasterinformationsystem.feature.home.generated.resources.just_logo_2
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
        val res: DrawableResource = Res.drawable.just_logo_2
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

@Composable
fun AppNameLogoSection(
    modifier: Modifier = Modifier,
) {

    val text = buildAnnotatedString {
        append(AnnotatedString(text = "Disaster ", spanStyle = SpanStyle(MaterialTheme.colorScheme.error)))
        append(AnnotatedString(text = "Information ", spanStyle = SpanStyle(Color.Blue)))
        append(AnnotatedString(text = "System"))
    }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.DataExploration,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = text,
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 17.sp,
                fontFamily = FontFamily.Serif
            )
        )
    }


}
