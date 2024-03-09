package data_submission.ui.form

import data_submission.ui.form.components.DatePickerDate
import data_submission.ui.form.components.TimePickerData


sealed interface FormEvent
{
    data class TitleChanged(val title: String) : FormEvent
    data class DateChanged(val date: DatePickerDate) : FormEvent
    data class StartTimeChanged(val time: TimePickerData) : FormEvent
    data class EndTimeChanged(val time: TimePickerData) : FormEvent
    data class LocationChanged(val location: String) : FormEvent
    data class DescriptionChanged(val description: String) : FormEvent

}