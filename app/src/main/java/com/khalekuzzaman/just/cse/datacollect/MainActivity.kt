package com.khalekuzzaman.just.cse.datacollect

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.datacollect.connectivity.ConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.connectivity.NetworkConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.navigaion.RootNavHost
import com.khalekuzzaman.just.cse.datacollect.ui.theme.AppTheme
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.TIRAMISU)

class MainActivity : ComponentActivity() {
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContent {
            val status by connectivityObserver.observe().collectAsState(
                initial = ConnectivityObserver.Status.Unavailable
            )
            AppTheme {
                Scaffold(
                    snackbarHost = {
                        if (status != ConnectivityObserver.Status.Available) {
                            CustomSnackBar(message = "Internet unavailable", isErrorMessage = true)
                        } else {
                            CustomSnackBar(message = "Internet Available", isErrorMessage = false)
                        }
                    }
                ) {
                    RootNavHost(Modifier.padding(it))
                }


            }
        }
    }

}

@Composable
fun CustomSnackBar(
    message: String,
    isErrorMessage: Boolean
) {
    var show by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(Unit) {
        delay(4000)
        show = false
    }
    val containerColor = if (isErrorMessage) Color.Red else Color.Green
    val textColor = if (isErrorMessage) Color.White else Color.Black
    if (show) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            containerColor = containerColor,
            contentColor = textColor,
        ) {
            Text(message)
        }
    }


}

