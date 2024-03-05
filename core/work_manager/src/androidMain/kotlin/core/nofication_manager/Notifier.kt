package core.nofication_manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import core.work_manager.R


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

// Define a class for standard notifications
class StandardNotificationBuilder(context: Context) : BaseNotificationBuilder(context) {
     fun notify(title: String, message: String, notificationId: Int = 1) {
        val channelID = "channel_1"
        val channel = createChannel(channelId = channelID, channelName = "Channel_01")
        attachChannelToManager(channel)
        val notification = NotificationCompat.Builder(context, channelID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.add_photo)
            .setAutoCancel(true)
            .build()
        manager.notify(notificationId, notification)
    }
}

// Define a class for notifications with a progress bar
/**
 * @param id should be unique.
 * @param target is the target or max value of the progress ,in which value programs is 100% completed
 */
class ProgressNotificationBuilder(
    title: String,
    message: String,
    private val id:Int,
    private val target:Int,
    context: Context
) : BaseNotificationBuilder(context) {
    /*
    The trick is to replace the old notification with new once,
    so we have to keep the id as same and unique,
    do not expose the id to the client to avoid bug
     */
    private val channelID = "channel_1"
    private val channel = createChannel(channelId = channelID, channelName = "Channel_01")
    private val notificationBuilder = NotificationCompat.Builder(context, channelID)
        .setContentTitle(title)
        .setContentText(message)
        .setSmallIcon(R.drawable.add_photo)
        .setAutoCancel(false)
    fun updateProgress(current: Int) {
        attachChannelToManager(channel)
        val notification = notificationBuilder
            .setProgress(target, current, false)
            .build()
        manager.notify(id, notification)
    }


}
