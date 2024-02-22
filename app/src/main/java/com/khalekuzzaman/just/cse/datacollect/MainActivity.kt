package com.khalekuzzaman.just.cse.datacollect

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.khalekuzzaman.just.cse.datacollect.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme{
                MultiplePhotoPicker()
//                Column (
//                    modifier = Modifier.verticalScroll(rememberScrollState())
//                ){
//                    Text(text = "Welcome to Data collect")
//                    PermissionIfNeeded()
//
//                    SingleVideoPicker()
//                }
            }
        }
    }
}

