package data_submission.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data_submission.domain.ReportFormController
import data_submission.ui.form.CompactForm


@Composable
fun ReportFormCommon(
    formStateManager: ReportFormController,
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
            modifier = Modifier.padding(16.dp).fillMaxSize().verticalScroll(rememberScrollState())
            ,//making it scrollable,it parent can be crash,if it parent is a  sub compose layout(Lazy List,Scaffold),
            data = formStateManager.fromState.collectAsState().value,
            onEvent = formStateManager::onFormEvent
        )

    }


}
