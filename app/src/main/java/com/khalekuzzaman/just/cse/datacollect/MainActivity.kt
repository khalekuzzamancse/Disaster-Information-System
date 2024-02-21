package com.khalekuzzaman.just.cse.datacollect

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.khalekuzzaman.just.cse.datacollect.ui.theme.DataCollectTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataCollectTheme {
                Column (
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ){
                    Text(text = "Welcome to Data collect")
                    PermissionIfNeeded()
                    MultiplePhotoPicker()
                    SingleVideoPicker()
                }

            }
        }
    }
}

