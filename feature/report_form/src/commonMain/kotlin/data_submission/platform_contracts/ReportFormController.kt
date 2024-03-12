package data_submission.platform_contracts

import kotlinx.coroutines.flow.StateFlow
import data_submission.ui.form.FormEvent
import data_submission.ui.form.FormState
import data_submission.ui.form.ReportFormControllerImpl
import kotlinx.coroutines.flow.Flow

/**
 * * Defining the contract so that if a specific platform need to use it own
 * state management solution such as ViewModel for android platform then they can do that
 * * Also defining a base state management ( [ReportFormControllerImpl]) that has some common code involved that works in
 * any platform and need to be reacted
 */
interface ReportFormController {
    val fromState:StateFlow<FormState>
    fun onFormEvent(event: FormEvent)
}