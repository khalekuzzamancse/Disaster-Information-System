package data_submission.ui.form

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import data_submission.domain.ReportFormController
import data_submission.platform_contracts.DateUtilsCustom
import data_submission.ui.form.components.TimePickerData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReportFormControllerImpl(private val dateUtil: DateUtilsCustom) :
    ReportFormController() {
    private val _state = MutableStateFlow(
        FormState(
            title = "",
            date = dateUtil.getCurrentDate(),
            startTime = TimePickerData(2, 30),
            endTime = TimePickerData(2, 30),
            location = "",
            description = ""
        )
    )
   override val fromState = _state.asStateFlow()


   override fun onFormEvent(event: FormEvent) {
        val newState = when (event) {
            is FormEvent.TitleChanged -> _state.value.copy(title = event.title)
            is FormEvent.DateChanged -> _state.value.copy(date = dateUtil.getDate(event.date.dateMillisecond))
            is FormEvent.StartTimeChanged -> _state.value.copy(startTime = event.time)
            is FormEvent.EndTimeChanged -> _state.value.copy(endTime = event.time)
            is FormEvent.LocationChanged -> _state.value.copy(location = event.location)
            is FormEvent.DescriptionChanged -> _state.value.copy(description = event.description)
        }
        _state.value = newState
    }

    override val  isFormValid=_state.map { it.areAllFieldsFilled() }

}
