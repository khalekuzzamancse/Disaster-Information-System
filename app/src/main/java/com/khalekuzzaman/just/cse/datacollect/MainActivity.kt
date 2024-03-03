package com.khalekuzzaman.just.cse.datacollect

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.khalekuzzaman.just.cse.datacollect.chat_ui.CustomSnackBar
import com.khalekuzzaman.just.cse.datacollect.chat_ui.SnackBarMessage
import com.khalekuzzaman.just.cse.datacollect.chat_ui.SnackBarMessageType
import com.khalekuzzaman.just.cse.datacollect.connectivity.ConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.connectivity.NetworkConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.navigaion.RootNavHost
import com.khalekuzzaman.just.cse.datacollect.ui.theme.AppTheme


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
                ) {
                    RootNavHost(Modifier.padding(it))
                }


            }
        }
    }

}


