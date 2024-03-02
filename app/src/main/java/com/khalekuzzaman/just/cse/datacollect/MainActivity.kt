package com.khalekuzzaman.just.cse.datacollect

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.khalekuzzaman.just.cse.datacollect.chat_ui.CustomSnackBar
import com.khalekuzzaman.just.cse.datacollect.connectivity.ConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.connectivity.NetworkConnectivityObserver
import com.khalekuzzaman.just.cse.datacollect.navigaion.RootNavHost
import com.khalekuzzaman.just.cse.datacollect.ui.theme.AppTheme


@RequiresApi(Build.VERSION_CODES.TIRAMISU)

class MainActivity : ComponentActivity() {
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        val context=this
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContent {
            val status by connectivityObserver.observe().collectAsState(
                initial = ConnectivityObserver.Status.Unavailable
            )
            LaunchedEffect(Unit) {

//                val res = getRequest<List<Bank>>("http://192.168.10.154:8080/api/banks")
//                println("APIResponse:$res")
            }
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


