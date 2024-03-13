package report_form.platform_contracts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import report_form.ui.DesktopLocationPicker
import report_form.ui.form.components.PickedLocation

@Composable
actual fun LocationPicker(
    modifier: Modifier,
    onLocationPicked: (PickedLocation) -> Unit
) {
    DesktopLocationPicker(onLocationPicked)
}