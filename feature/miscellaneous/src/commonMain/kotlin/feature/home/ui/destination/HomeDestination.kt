package feature.home.ui.destination


import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import feature.home.ui.components.MyDropDownMenu
import feature.home.ui.components.welcome_section.WelcomeToHome
import ui.CustomSnackBar
import ui.SnackBarMessage


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@PublishedApi
@Composable
internal fun HomeDestination(
    snackBarMessage: SnackBarMessage? = null,
    enableSend: Boolean,
    onSendRequest: () -> Unit = {},
    onAboutUsRequest: () -> Unit = {},
    onContactUsRequest: () -> Unit = {},
) {
    val windowSize = calculateWindowSizeClass().widthSizeClass
    val compact = WindowWidthSizeClass.Compact
    val medium = WindowWidthSizeClass.Medium
    val expanded = WindowWidthSizeClass.Expanded

    AnimatedContent(windowSize) { window ->
        when (window) {
            compact, medium -> {
                _CompactHomeDestination(
                    snackBarMessage = snackBarMessage,
                    enableSend = enableSend,
                    onSendRequest = onSendRequest,
                    onAboutUsRequest = onAboutUsRequest,
                    onContactUsRequest = onContactUsRequest
                )
            }

            expanded -> {
                _ExpandedHome(
                    snackBarMessage = snackBarMessage,
                    enableSend = enableSend,
                    onSendRequest = onSendRequest
                )
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun _CompactHomeDestination(
    snackBarMessage: SnackBarMessage? = null,
    enableSend: Boolean,
    onSendRequest: () -> Unit = {},
    onAboutUsRequest: () -> Unit = {},
    onContactUsRequest: () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            if (snackBarMessage != null) {
                CustomSnackBar(snackBarMessage)
            }
        },
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    MyDropDownMenu(
                        modifier = Modifier.semantics (mergeDescendants = true){ contentDescription="More options" },
                        onAboutClick = onAboutUsRequest,
                        onContactUsClick = onContactUsRequest,
                    )
                }

            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .semantics { contentDescription = "Home Screen" },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            _WelcomeNSendButton(
                modifier = Modifier,
                onSendRequest = onSendRequest,
                enableSend = enableSend
            )


        }
    }

}

@Composable
private fun _ExpandedHome(
    modifier: Modifier = Modifier,
    snackBarMessage: SnackBarMessage? = null,
    enableSend: Boolean,
    onSendRequest: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = {
            if (snackBarMessage != null) {
                CustomSnackBar(snackBarMessage)
            }
        },
    ) { scaffoldPadding ->
        Row(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxSize()
                .semantics { contentDescription = "screen with home,about us and contact" },
            horizontalArrangement = Arrangement.Center
        ) {
            _WelcomeNSendButton(
                Modifier.weight(1f).align(Alignment.CenterVertically),
                onSendRequest = onSendRequest,
                enableSend = enableSend
            )
            AboutUsContactUs(
                modifier = Modifier
                    //TODO(.verticalScroll(rememberScrollState()) fix bug why crashes)
                    .weight(1f)

            )


        }
    }

}


@Composable
private fun _WelcomeNSendButton(
    modifier: Modifier = Modifier,
    enableSend: Boolean,
    onSendRequest: () -> Unit
) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WelcomeToHome(modifier = Modifier
            .semantics(mergeDescendants = true) { contentDescription = "Welcome section" })
        Spacer(Modifier.height(24.dp))
        _SentButton(
            modifier = Modifier,
            enable = enableSend,
            onClick = onSendRequest
        )
    }
}

@Composable
private fun _SentButton(
    modifier: Modifier = Modifier,
    enable: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enable,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = null,//used as group with parent
            tint = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primary),
        )
        Spacer(Modifier.width(8.dp))
        Text(text = "SEND REPORT TO SERVER")
    }
}

