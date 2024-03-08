package data_submission.platform_contracts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import data_submission.ui.DesktopLocationPicker
import ui.form.components.PickedLocation

@Composable
actual fun LocationPicker(
    modifier: Modifier,
    onLocationPicked: (PickedLocation) -> Unit
) {
    DesktopLocationPicker(onLocationPicked)
}