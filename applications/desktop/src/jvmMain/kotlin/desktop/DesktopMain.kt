package desktop

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import platform_contracts.DateUtilsCustom
import ui.form.BaseDescriptionFormManager
import ui.form.CompactForm
import ui.routes.SubmitFormRoutes


fun main() {
    application {
        val state = remember {
            WindowState(
                position = WindowPosition(0.dp, 0.dp),
            )
        }
        state.size = DpSize(width = 400.dp, height = 700.dp)
        Window(
            state = state,
            title = "Disaster Information System",
            onCloseRequest = ::exitApplication
        ) {
            MaterialTheme {

                val viewModel = remember { BaseDescriptionFormManager(DateUtilsCustom()) }
                SubmitFormRoutes(formStateManager = viewModel, onSubmitClick = {})
            }
        }
    }

}
