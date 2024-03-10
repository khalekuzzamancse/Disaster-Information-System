package core.nofication_manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

//taking context as instance variable so after using class instance make it eligible for garbage collection
//by assigning null to it  instance or use other technique so that it references is not hold unnecessary
// Define an interface for common notification builder functionalities

// Define the abstract base class to hold common properties and methods
abstract class BaseNotificationBuilder(
    protected val context: Context
)  {
    protected val manager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    protected fun createChannel(
        channelId: String, channelName: String,
        description: String = "notification channel description",
        enableVibration: Boolean = true, enableLights: Boolean = true,
        vibrationPattern: LongArray = longArrayOf(100, 200, 300, 400, 300, 200, 100),
        importance: Int = NotificationManager.IMPORTANCE_HIGH,
    ): NotificationChannel {
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            this.description = description
            this.vibrationPattern = vibrationPattern
            this.enableLights(enableLights)
            this.enableVibration(enableVibration)
        }
        return channel
    }

    protected fun attachChannelToManager(channel: NotificationChannel) {
        manager.createNotificationChannel(channel)
    }
}