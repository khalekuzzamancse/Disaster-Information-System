package data_submission.ui.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material.icons.twotone.Map
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data_submission.platform_contracts.LocationPicker
import data_submission.ui.components.DatePicker
import data_submission.ui.components.DatePickerDate
import data_submission.ui.components.DescriptionTextField
import data_submission.ui.components.FormTextField
import data_submission.ui.components.ReadOnlyTextField
import data_submission.ui.components.TimePickerCustom
import data_submission.ui.components.TimePickerData
import data_submission.ui.components.TwoPaneLayout


@Composable
fun CompactForm(
    modifier: Modifier = Modifier,
    data: FormState,
    onEvent: (FormEvent) -> Unit,
) {
    val fieldModifier = Modifier.fillMaxWidth()

    Form(
        modifier = modifier,
        fieldModifier = fieldModifier,
        title = data.title,
        onTitleChanged = { onEvent(FormEvent.TitleChanged(it)) },
        date = data.date,
        onDateChanged = { onEvent(FormEvent.DateChanged(it)) },
        startTime = data.startTime,
        onStartTime = { onEvent(FormEvent.StartTimeChanged(it)) },
        endTime = data.endTime,
        onEndTimeChanged = { onEvent(FormEvent.EndTimeChanged(it)) },
        location = data.location,
        onLocationChanged = { onEvent(FormEvent.LocationChanged(it)) },
        description = data.description,
        onDescriptionChanged = { onEvent(FormEvent.DescriptionChanged(it)) },
    )

}

/**
 * It is depends only primitive so it loosely coupled and test-able in isolation
 */

@Composable
private fun Form(
    modifier: Modifier = Modifier,
    fieldModifier: Modifier,
    title: String,
    onTitleChanged: (String) -> Unit,
    date: String,
    onDateChanged: (DatePickerDate) -> Unit,
    startTime: TimePickerData,
    onStartTime: (TimePickerData) -> Unit,
    endTime: TimePickerData,
    onEndTimeChanged: (TimePickerData) -> Unit,
    location: String,
    onLocationChanged: (String) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
) {

    var mapOpened by remember { mutableStateOf(false) }
    TwoPaneLayout(
        modifier = Modifier.fillMaxWidth(),
        showBothPanes = mapOpened,
        primaryPane = {
            _PrimitiveForm(
                modifier = modifier,
                fieldModifier = fieldModifier,
                title = title,
                onTitleChanged = onTitleChanged,
                date = date,
                onDateChanged = onDateChanged,
                startTime = startTime,
                onStartTime = onStartTime,
                endTime = endTime,
                onEndTimeChanged = onEndTimeChanged,
                location = location,
                onLocationPickedRequest = {
                    mapOpened = true
                },
                description = description,
                onDescriptionChanged = onDescriptionChanged,
            )
        },
        secondaryPane = {
            LocationPicker(
                modifier=modifier,
                onLocationPicked = {loc->
                    mapOpened=false
                    onLocationChanged("$loc")
                })
        }
    )

}

@Composable
private fun _PrimitiveForm(
    modifier: Modifier = Modifier,
    fieldModifier: Modifier,
    title: String,
    onTitleChanged: (String) -> Unit,
    date: String,
    onDateChanged: (DatePickerDate) -> Unit,
    startTime: TimePickerData,
    onStartTime: (TimePickerData) -> Unit,
    endTime: TimePickerData,
    onEndTimeChanged: (TimePickerData) -> Unit,
    location: String,
    onLocationPickedRequest: () -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
) {
    val colors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
    )
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        FormTextField(
            modifier = fieldModifier,
            label = "Title",
            value = title,
            onValueChanged = onTitleChanged,
            leadingIcon = Icons.Outlined.Title,
        )

        DatePicker(
            modifier = fieldModifier,
            label = "Event Date",
            value = date,
            leadingIcon = Icons.Outlined.DateRange,
            trailingIcon = Icons.Default.Edit,
            onDateSelected = { onDateChanged(it) },
            colors = colors
        )
        TimePickerCustom(
            modifier = fieldModifier,
            label = "Event Start Time",
            value = startTime,
            leadingIcon = Icons.Outlined.Timer,
            trailingIcon = Icons.Default.Edit,
            onTimeSelected = onStartTime,
            colors = colors
        )

        TimePickerCustom(
            modifier = fieldModifier,
            label = "Event End Time",
            value = endTime,
            leadingIcon = Icons.Outlined.Timer,
            trailingIcon = Icons.Default.Edit,
            onTimeSelected = onEndTimeChanged,
            colors = colors
        )

        ReadOnlyTextField(
            modifier = fieldModifier,
            label = "Location",
            value = location,
            leadingIcon = Icons.TwoTone.Map,
            trailingIcon = Icons.Default.Edit,
            onTrailingIconClick = onLocationPickedRequest,
            colors = colors
        )

        DescriptionTextField(
            modifier = fieldModifier,
            label = "Description",
            value = description,
            leadingIcon = Icons.Outlined.Description,
            onValueChanged = onDescriptionChanged,
        )


    }

}



