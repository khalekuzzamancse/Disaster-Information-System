package com.khalekuzzaman.just.cse.datacollect

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.khalekuzzaman.just.cse.datacollect.ui_layer.theme.AppTheme
import data_submission.platform_contracts.DateUtilsCustom
import data_submission.ui.routes.SubmitFormRoutes
import ui.form.BaseDescriptionFormManager


@RequiresApi(Build.VERSION_CODES.TIRAMISU)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
              //  RootNavHost()
                SubmitFormRoutes(
                    BaseDescriptionFormManager(
                        DateUtilsCustom(),
                    )
                )
            }
        }
    }

}

