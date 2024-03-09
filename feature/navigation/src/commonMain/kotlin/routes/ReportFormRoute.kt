package routes

import androidx.compose.runtime.Composable
import data_submission.ui.form.BaseDescriptionFormManager
import data_submission.ui.routes.ReportFormCommon

@Composable
fun ReportFormRoute(
    formStateManager: BaseDescriptionFormManager,
) {
    ReportFormCommon(
        formStateManager = formStateManager
    )


}