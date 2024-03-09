package data_submission.ui.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data_submission.ui.form.BaseDescriptionFormManager
import data_submission.ui.form.CompactForm
import data_submission.ui.form.FormEvent
import data_submission.ui.form.FormState


@Composable
fun ReportFormCommon(
    formStateManager: BaseDescriptionFormManager,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier,
        //defining the scroll here causes the google map problem
        //while scrolling,because the scroll consume the drag event of the marker
        //so avoid the form as scrollable
        //try better solution

        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CompactForm(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            data = formStateManager.fromState.collectAsState().value,
            onEvent = formStateManager::onFormEvent
        )

    }


}
