package feature.home.ui.destination

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import disasterinformationsystem.feature.home.generated.resources.Res
import disasterinformationsystem.feature.home.generated.resources.ower_image
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@PublishedApi
@Composable
internal fun AboutUs() {
    // Assuming we have a function to open URLs
    val openUrl = { url: String ->
        // Implement URL opening logic here
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val res: DrawableResource = Res.drawable.ower_image
        //after that compile it again to generate Res class, use : .\gradlew generateComposeResClass
        Image(
            modifier=Modifier.size(80.dp).clip(CircleShape),
            painter = painterResource(res),//org.jetbrains.compose.resources.
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Mahedi Hasan", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
       Text(
            text = AnnotatedString.Builder().apply {
                append("Mahedi Hasan received the B.Sc. (Eng.) degree in information and communication technology from ")
                pushStringAnnotation(tag = "URL", annotation = "https://mbstu.ac.bd")
                withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
                    append("Mawlana Bhashani Science and Technology University (MBSTU)")
                }
                pop()
                append(", Tangail, Bangladesh, in 2013, and the M.Sc. degree in database technology from ")
                pushStringAnnotation(tag = "URL", annotation = "https://spbu.ru")
                withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
                    append("Saint Petersburg State University")
                }
                pop()
                append(", Russia, in 2016. He is currently working as a lecturer in the Computer Science and Engineering department of ")
                pushStringAnnotation(tag = "URL", annotation = "https://just.edu.bd")
                withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
                    append("Jashore University of Science and Technology University (JUST)")
                }
                pop()
                append(", Bangladesh. He previously worked as a lecturer with the Department of Computer Science and Engineering (CSE), National Institute of Textile Engineering and Research (NITER), Dhaka, Bangladesh, since March 2020. His research interests include Bayesian Inference, Data Science, Machine Learning (ML), and Natural Language Processing (NLP).")
            }.toAnnotatedString(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,

        )
    }
}
