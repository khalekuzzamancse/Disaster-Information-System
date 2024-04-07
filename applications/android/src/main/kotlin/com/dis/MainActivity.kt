package com.dis

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dis.theme.DisasterInfoSystemTheme
import platform_contract.NavigationRoot


@RequiresApi(Build.VERSION_CODES.TIRAMISU)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            DisasterInfoSystemTheme {
                NavigationRoot()
            }
        }
    }


}


