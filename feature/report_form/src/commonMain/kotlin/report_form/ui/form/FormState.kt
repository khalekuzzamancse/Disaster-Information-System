package report_form.ui.form

import report_form.ui.form.components.TimePickerData
@PublishedApi
internal data class FormState(
    var title: String,
    var date: String,
    var startTime: TimePickerData,
    var endTime: TimePickerData,
    var location: String = "",
    var description: String = ""
){
    fun areAllFieldsFilled(): Boolean {
        return title.isNotBlank() &&
                date.isNotBlank() &&
                startTime.isValid() &&
                endTime.isValid() &&
                location.isNotBlank() &&
                description.isNotBlank()
    }
}
