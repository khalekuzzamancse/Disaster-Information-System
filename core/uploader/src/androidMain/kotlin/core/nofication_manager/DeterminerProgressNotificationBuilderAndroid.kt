package core.nofication_manager

import android.content.Context
import androidx.core.app.NotificationCompat
import core.work_manager.R
import kotlin.properties.Delegates

internal class DeterminerProgressNotificationBuilderAndroid(context: Context) : BaseNotificationBuilder(context) {
    /*
    The trick is to replace the old notification with new once,
    so we have to keep the id as same and unique,
    do not expose the id to the client to avoid bug
     */
    private val channelID = "channel_1"
    private val channel = createChannel(channelId = channelID, channelName = "Channel_01")
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private var notificationId by Delegates.notNull<Int>()
    private var target by Delegates.notNull<Int>()
    /**
     * @param id should be unique.
     * @param target is the target or max value of the progress ,in which value programs is 100% completed
     */
    fun build(
        title: String,
        message: String,
        id: Int,
        target: Int,
    ) {
        notificationId = id
        this.target = target
        notificationBuilder = NotificationCompat.Builder(context, channelID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.add_icon)
            .setAutoCancel(false)
    }

    fun updateProgress(current: Int) {
        attachChannelToManager(channel)
        val notification = notificationBuilder
            .setProgress(target, current, false)
            .build()
        manager.notify(notificationId, notification)
    }


}
