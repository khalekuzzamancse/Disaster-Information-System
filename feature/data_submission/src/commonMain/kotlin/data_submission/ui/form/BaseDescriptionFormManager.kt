package ui.form

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import data_submission.platform_contracts.DescriptionFormStateManager
import data_submission.platform_contracts.DateUtilsCustom
import ui.form.components.TimePickerData

class BaseDescriptionFormManager(private val dateUtil: DateUtilsCustom) :
    DescriptionFormStateManager {
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
}
