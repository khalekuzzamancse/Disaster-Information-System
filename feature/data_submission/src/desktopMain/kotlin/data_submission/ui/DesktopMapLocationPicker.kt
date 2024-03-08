package data_submission.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ui.form.components.PickedLocation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DesktopLocationPicker(
    onLocationPicked: (PickedLocation) -> Unit
) {
    var location by remember {
        mutableStateOf<PickedLocation?>(PickedLocation(10.0, 20.5))
    }

    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Location") },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = hostState)
        },
    ) { innerPadding ->
        Column(Modifier.fillMaxSize().padding(innerPadding)) {
            Text("Not implemented yet...")
            Button(onClick = {
                scope.launch {
                    if (location == null)
                        hostState.showSnackbar("Click marker to select location")
                    location?.let { loc ->
                        location = loc
                        onLocationPicked(loc)
                    }

                }
            }) {
                Text(
                    text = "Confirm",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
            }

        }


    }

}