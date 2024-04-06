package platform_contracts

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class StandardNotificationBuilder {
    fun notify(title: String, message: String, notificationId: Int = 1)
}
