package platform_contracts

import android.content.Context
import core.nofication_manager.StandardNotificationBuilderAndroid

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class StandardNotificationBuilder(context:Context) {
    private val builder= StandardNotificationBuilderAndroid(context)
    actual fun notify(title: String, message: String, notificationId: Int) {
        builder.notify(title = title, message = message, notificationId = notificationId)
    }


}