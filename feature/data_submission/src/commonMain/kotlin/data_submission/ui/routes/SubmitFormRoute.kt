package data_submission.ui.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.form.BaseDescriptionFormManager
import ui.form.CompactForm

@Composable
fun SubmitFormRoutes(
    formStateManager: BaseDescriptionFormManager,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier,//find why vertical scroll causes crash on  google map selection
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CompactForm(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            data = formStateManager.fromState.collectAsState().value,
            onEvent = formStateManager::onFormEvent
        )

    }


}