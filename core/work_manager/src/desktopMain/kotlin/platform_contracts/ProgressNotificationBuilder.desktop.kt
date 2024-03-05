@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package platform_contracts

actual class ProgressNotificationBuilder {
    actual fun build(
        title: String,
        message: String,
        id: Int,
        target: Int
    ) {
    }

    actual fun updateProgress(current: Int) {
    }

}