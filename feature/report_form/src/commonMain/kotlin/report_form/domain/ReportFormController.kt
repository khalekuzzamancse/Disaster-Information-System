package report_form.domain

import kotlinx.coroutines.flow.StateFlow
import report_form.ui.form.FormEvent
import report_form.ui.form.FormState
import report_form.ui.form.ReportFormControllerImpl
import kotlinx.coroutines.flow.Flow

/**
 * * Defining the contract so that if a specific platform need to use it own
 * state management solution such as ViewModel for android platform then they can do that
 * * Also defining a base state management ( [ReportFormControllerImpl]) that has some common code involved that works in
 * any platform and need to be reacted
 * * Making the field as internal so that state is accessible within the module
 * so that client module has not access to this state.
 * Interface proper needed to be public that is why using abstract class
 * * [isFormValid] is public so that the client can observe the form is filled or not right way,so that
 * based on the validity it can show something to user
 */

abstract class ReportFormController {
    internal abstract val fromState: StateFlow<FormState>
    internal abstract fun onFormEvent(event: FormEvent)
    abstract val  isFormValid:Flow<Boolean>

}