package feature.home.ui.destination


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.home.ui.MyDropDownMenu
import feature.home.ui.welcome_section.WelcomeToHome
import ui.CustomSnackBar
import ui.SnackBarMessage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDestination(
    snackBarMessage: SnackBarMessage?=null,
    isSending: Boolean,
    onSend: () -> Unit = {},
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
                    SentButton(enable = !isSending, onSend)
                    MyDropDownMenu()
                }

            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WelcomeToHome()


        }
    }

}

@Composable
private fun SentButton(enable: Boolean, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        enabled = enable
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = null,
            tint = if (enable) MaterialTheme.colorScheme.primary else LocalContentColor.current,
        )
    }
}

