package routes

import androidx.compose.runtime.Composable
import data_submission.ui.form.ReportFormControllerImpl
import data_submission.ui.navigation.ReportFormCommon

@Composable
fun ReportFormRoute(
    formStateManager: ReportFormControllerImpl,
) {
    ReportFormCommon(
        formStateManager = formStateManager
    )


}