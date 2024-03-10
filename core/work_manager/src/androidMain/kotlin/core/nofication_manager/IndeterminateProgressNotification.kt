package core.nofication_manager

import android.content.Context
import androidx.core.app.NotificationCompat
import core.work_manager.R

class IndeterminateProgressNotification(
    private val id: Int,
    title: String,
    private val message: String,
    context: Context
) : BaseNotificationBuilder(context) {
    /*
    The trick is to replace the old notification with new once,
    so we have to keep the id as same and unique,
    do not expose the id to the client to avoid bug
     */
    private val channelID = "channel_1"
    private val channel = createChannel(channelId = channelID, channelName = "Channel_01")

    private val max = 10
    private val progress = 0
    private val notificationBuilder = NotificationCompat.Builder(context, channelID)
        .setContentTitle(title)
        .setContentText(message)
        .setSmallIcon(R.drawable.add_photo)
        .setOngoing(true)
        .setAutoCancel(false)


    /**
     * @param id should be unique
     * @param target is the target or max value of the progress ,in which value programs is 100% completed
     */
    fun start() {
        attachChannelToManager(channel)
        val notification = notificationBuilder
            .setProgress(max, progress, true)
            .build()
        manager.notify(id, notification)
    }

    fun stop(message: String = this.message)=replaceNotificationById(message)
    private fun replaceNotificationById(message: String){
        val notification = notificationBuilder
            .setContentText(message)
            .setProgress(max, max, false)
            .setOngoing(false)
            .build()
        manager.notify(id, notification)
    }


}
