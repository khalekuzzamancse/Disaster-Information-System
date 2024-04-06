package platform_contracts

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ProgressNotificationBuilder {
    fun build(
        title: String,
        message: String,
        id: Int,
        target: Int
    )

    fun updateProgress(current: Int)
}
