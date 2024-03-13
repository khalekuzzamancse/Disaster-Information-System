package report_form.ui.form

import report_form.ui.form.components.DatePickerDate
import report_form.ui.form.components.TimePickerData

@PublishedApi
internal sealed interface FormEvent
{
    data class TitleChanged(val title: String) : FormEvent
    data class DateChanged(val date: DatePickerDate) : FormEvent
    data class StartTimeChanged(val time: TimePickerData) : FormEvent
    data class EndTimeChanged(val time: TimePickerData) : FormEvent
    data class LocationChanged(val location: String) : FormEvent
    data class DescriptionChanged(val description: String) : FormEvent

}