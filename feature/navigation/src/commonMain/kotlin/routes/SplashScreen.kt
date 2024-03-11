package routes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import disasterinformationsystem.feature.navigation.generated.resources.Res
import disasterinformationsystem.feature.navigation.generated.resources.app_logo
import disasterinformationsystem.feature.navigation.generated.resources.just_logo_transparent
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 * * Needed refactor
 * * Since right now unable to read image as shared source,that is why delegating
 * the platform to load the image from their own resource and the logo composable.
 * fix it later using "Res" class  of compose multiplatform resource
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier
) {

    val primary = MaterialTheme.colorScheme.primary
    MaterialTheme.colorScheme.secondary
    val tertiary = MaterialTheme.colorScheme.tertiary
    val largeRadialGradient = object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val biggerDimension = maxOf(size.height, size.width)
            return RadialGradientShader(
                colors = listOf(primary, tertiary),
                center = size.center,
                radius = biggerDimension / 2f,
                colorStops = listOf(0f, 0.95f)
            )
        }
    }
    //     colors = listOf(Color(0xFF2be4dc), Color(0xFF243484)),

    Box(
        modifier.fillMaxSize().background(largeRadialGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val res: DrawableResource = Res.drawable.app_logo
            //after that compile it again to generate Res class, use : .\gradlew generateComposeResClass
            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(res),//org.jetbrains.compose.resources.
                contentDescription = null,
//                colorFilter = ColorFilter.tint(
//                        MaterialTheme.colorScheme.tertiaryContainer
//
//                )
            )
            Spacer(Modifier.height(32.dp))
            WelcomeSection(Modifier.align(Alignment.CenterHorizontally))
            Spacer(Modifier.height(8.dp))
            AppNameLogoSection(Modifier)

            DevelopedBy(Modifier)
        }
    }


}

@Composable
private fun WelcomeSection(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = "Welcome To ",
        color = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.tertiary),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif
        )
    )
}

@Composable
private fun AppNameLogoSection(
    modifier: Modifier = Modifier,
) {
    var showAppName by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(40)
        showAppName = true
    }
    val text = buildAnnotatedString {
        append(
            AnnotatedString(
                text = "Disaster",
                spanStyle = SpanStyle(MaterialTheme.colorScheme.secondary)
            )
        )
        append(
            AnnotatedString(
                text = " Information",
                spanStyle = SpanStyle(MaterialTheme.colorScheme.tertiary)
            )
        )
        append(
            AnnotatedString(
                text = " System",
                spanStyle = SpanStyle(MaterialTheme.colorScheme.primary)
            )
        )
    }
    AnimatedVisibility(
        visible = showAppName,
        enter = slideInVertically {
            it * 1000
        }
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = text,
                style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace
                )
            )
        }
    }


}

@Composable
private fun DevelopedBy(
    modifier: Modifier = Modifier,
) {

    val dept = buildAnnotatedString {
        append(
            AnnotatedString(
                text = "Developed by ",
                spanStyle = SpanStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight(400)
                )
            )
        )
        append(
            AnnotatedString(
                text = "Department of ",
                spanStyle = SpanStyle(
                    fontSize = 17.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight(500)
                )
            )
        )
        append(
            AnnotatedString(
                text = "CSE",
                spanStyle = SpanStyle(
                    fontSize = 18.sp,
                    color = Color.Blue,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight(600),
                    letterSpacing = 2.sp
                )
            )
        )
    }
    val university = buildAnnotatedString {
        append(
            AnnotatedString(
                text = "Jashore University Of Science and Technology",
                spanStyle = SpanStyle(
                    color = MaterialTheme.colorScheme.surface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight(300),
                )
            ),
            AnnotatedString(
                text = "(JUST)",
                spanStyle = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight(500),
                    letterSpacing = 2.sp
                )
            )
        )
    }
    Column(modifier) {
        Text(
            text = dept,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = university,
        )
    }

}

