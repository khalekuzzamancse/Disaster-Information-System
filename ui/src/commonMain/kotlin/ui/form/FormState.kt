package ui.form

import ui.form.components.TimePickerData

data class FormState(
    var title: String ,
    var date: String ,
    var startTime: TimePickerData,
    var endTime: TimePickerData,
    var location: String = "",
    var description: String = ""
)
