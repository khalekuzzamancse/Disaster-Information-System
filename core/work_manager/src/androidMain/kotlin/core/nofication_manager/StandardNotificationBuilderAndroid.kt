package core.nofication_manager

import android.content.Context
import androidx.core.app.NotificationCompat
import core.work_manager.R

// Define a class for standard notifications
internal class StandardNotificationBuilderAndroid(context: Context) : BaseNotificationBuilder(context) {
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