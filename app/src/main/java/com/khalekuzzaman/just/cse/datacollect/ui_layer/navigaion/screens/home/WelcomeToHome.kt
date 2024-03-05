package com.khalekuzzaman.just.cse.datacollect.ui_layer.navigaion.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeToHome(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Welcome to", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.width(16.dp))
        AppNameLogoSection(modifier = Modifier)

    }


}

