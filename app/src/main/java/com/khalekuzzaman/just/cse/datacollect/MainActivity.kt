package com.khalekuzzaman.just.cse.datacollect

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.khalekuzzaman.just.cse.datacollect.ui_layer.navigaion.RootNavHost
import com.khalekuzzaman.just.cse.datacollect.ui_layer.theme.AppTheme


@RequiresApi(Build.VERSION_CODES.TIRAMISU)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            this
            AppTheme {
                RootNavHost()
            }
        }
    }

}
