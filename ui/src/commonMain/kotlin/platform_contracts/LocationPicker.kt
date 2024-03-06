package platform_contracts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.form.components.PickedLocation

/**
 * * Defining DateUtil as platform specif because Java code not run in IOS
 * so the commonMain should not have any have dependency
 */
@Composable
expect fun LocationPicker(
    modifier: Modifier = Modifier,
    onLocationPicked: (PickedLocation) -> Unit
)