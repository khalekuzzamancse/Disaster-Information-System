package com.khalekuzzaman.just.cse.datacollect.chat_ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddToPhotos
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khalekuzzaman.just.cse.datacollect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    pickedImageCount:Int=0,
    pickedVideoCount: Int=0,
    onVideoPickRequest: () -> Unit,
    onImagePickRequest: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    BadgedBox(badge = {
                        if (pickedImageCount>0)
                        Badge {
                            Text(text = "$pickedImageCount")
                        }

                    }) {
                        IconButton(onClick = onImagePickRequest) {
                            Icon(
                                painter = painterResource(id= R.drawable.add_photo),
                                contentDescription = "add photo",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    BadgedBox(badge ={
                        if (pickedVideoCount>0)
                        Badge {
                            Text(text = "$pickedVideoCount")
                        }
                    } ) {

                        IconButton(onClick = onVideoPickRequest) {
                            Icon(
                                painter = painterResource(id= R.drawable.add_video),
                                contentDescription = "add video",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(30.dp)

                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }

                }

            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WelcomeToHome()
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                MessageInputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                )

            }

        }
    }


}


/**
 * * Needed refactor
 * * Since right now unable to read image as shared source,that is why delegating
 * the platform to load the image from their own resource and the logo composable.
 * fix it later using "Res" class  of compose multiplatform resource
 */
@Composable
fun WelcomeToHome(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Welcome to", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.width(16.dp))
        AppNameLogoSection(modifier = Modifier)

    }


}

@Composable
private fun AppNameLogoSection(
    modifier: Modifier = Modifier,
) {

    val text = buildAnnotatedString {
        append(
            AnnotatedString(
                text = "J",
                spanStyle = SpanStyle(MaterialTheme.colorScheme.primary)
            )
        )
        append(AnnotatedString(text = "U", spanStyle = SpanStyle(MaterialTheme.colorScheme.error)))
        append(AnnotatedString(text = "S", spanStyle = SpanStyle(MaterialTheme.colorScheme.error)))
        append(
            AnnotatedString(
                text = "T",
                spanStyle = SpanStyle(MaterialTheme.colorScheme.primary)
            )
        )
        append(AnnotatedString(" Data Collector"))
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
                fontSize = 16.sp,
                fontFamily = FontFamily.Cursive
            )
        )
    }


}