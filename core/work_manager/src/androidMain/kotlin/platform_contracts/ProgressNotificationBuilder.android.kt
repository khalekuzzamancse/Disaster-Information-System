package platform_contracts

import android.content.Context
import core.nofication_manager.DeterminerProgressNotificationBuilderAndroid

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ProgressNotificationBuilder(context: Context) {
    private val builder= DeterminerProgressNotificationBuilderAndroid(context)
    actual fun build(
        title: String,
        message: String,
        id: Int,
        target: Int
    ) {
        builder.build(title = title, message = message, id = id, target = target)
    }

    actual fun updateProgress(current: Int) {
        builder.updateProgress(current)
    }

}