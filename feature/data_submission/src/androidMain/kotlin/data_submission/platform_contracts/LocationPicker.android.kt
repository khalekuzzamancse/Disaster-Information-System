package data_submission.platform_contracts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import data_submission.ui.GMapLocationPicker
import data_submission.ui.components.PickedLocation

@Composable
actual fun LocationPicker(
    modifier: Modifier,
    onLocationPicked: (PickedLocation) -> Unit
) {
    GMapLocationPicker(onLocationPicked = onLocationPicked)
}