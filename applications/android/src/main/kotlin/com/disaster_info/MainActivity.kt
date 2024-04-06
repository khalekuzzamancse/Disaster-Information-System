package com.disaster_info

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.disaster_info.theme.DisasterInfoSystemTheme
import platform_contract.NavigationRoot


@RequiresApi(Build.VERSION_CODES.TIRAMISU)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisasterInfoSystemTheme {
                NavigationRoot()
            }
        }
    }


}


