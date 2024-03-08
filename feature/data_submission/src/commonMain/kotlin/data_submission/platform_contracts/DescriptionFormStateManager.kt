package data_submission.platform_contracts

import kotlinx.coroutines.flow.StateFlow
import ui.form.FormEvent
import ui.form.FormState
import ui.form.BaseDescriptionFormManager

/**
 * * Defining the contract so that if a specific platform need to use it own
 * state management solution such as ViewModel for android platform then they can do that
 * * Also defining a base state management ( [BaseDescriptionFormManager]) that has some common code involved that works in
 * any platform and need to be reacted
 */
interface DescriptionFormStateManager {
    val fromState:StateFlow<FormState>
    fun onFormEvent(event: FormEvent)
}