package platform_contract

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import data_submission.ui.form.BaseDescriptionFormManager
import di.DateUtilsProvider
import routes.Destination
import routes.HomeRoute
import routes.ImagePickerRoute
import routes.NavLayoutDecorator
import routes.ReportFormRoute
import routes.VideoPickerRoute

@Composable
actual fun NavigationRoot() {
    var selected by remember { mutableStateOf(Destination.HOME) }
    val formManager = remember { BaseDescriptionFormManager(DateUtilsProvider.dateUtil) }
    NavLayoutDecorator(
        selected=selected,
        onDestinationSelected = {
            selected=it
        },
    ) {
        AnimatedContent(selected) { destination ->
            when (destination) {
                Destination.HOME -> {
                    HomeRoute(
                        onSend = {},
                        isSending = false
                    )
                }

                Destination.REPORT_FORM -> {
                    ReportFormRoute(formManager)
                }

                Destination.IMAGE_PICKER -> {
                    ImagePickerRoute()
                }

                Destination.VIDEO_PICKER -> {
                    VideoPickerRoute()
                }

            }

        }

    }
}