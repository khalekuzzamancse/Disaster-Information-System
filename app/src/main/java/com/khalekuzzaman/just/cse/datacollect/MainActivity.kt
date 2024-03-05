package com.khalekuzzaman.just.cse.datacollect

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import com.khalekuzzaman.just.cse.datacollect.ui_layer.navigaion.RootNavHost
import com.khalekuzzaman.just.cse.datacollect.ui_layer.theme.AppTheme
import kotlinx.serialization.Serializable
import platform_contracts.NetworkRequests


@RequiresApi(Build.VERSION_CODES.TIRAMISU)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(key1 = Unit) {
                NetworkRequests
            }
            AppTheme {
                RootNavHost()
            }
        }
    }

}
@Serializable
data class Bank(
    val account: String,
    val trust: Double,
    val fee: Int
)

