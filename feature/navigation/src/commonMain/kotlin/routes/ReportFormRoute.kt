package routes

import androidx.compose.runtime.Composable
import report_form.ui.form.ReportFormControllerImpl
import report_form.ui.navigation.ReportForm

@Composable
fun ReportFormRoute(
    formStateManager: ReportFormControllerImpl,
) {
    ReportForm(
        formStateManager = formStateManager
    )


}