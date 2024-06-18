package com.meone.montir.helper.alarmNotification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.meone.montir.R
import com.meone.montir.view.sleep.StopTrackerActivity

class RunnerNotifier(
    private val notificationManager: NotificationManager,
    private val context: Context
) : Notifier(notificationManager) {

    override val notificationChannelId: String = "runner_channel_id"
    override val notificationChannelName: String = "Running Notification"
    override val notificationId: Int = 200

    override fun buildNotification(): Notification {
        val intent = Intent(context, StopTrackerActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle(getNotificationTitle())
            .setContentText(getNotificationMessage())
            .setSmallIcon(R.drawable.brand_logo)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    override fun getNotificationTitle(): String {
        return "MONTIR: Alarm Notification️"
    }

    override fun getNotificationMessage(): String {
        return "Saat nya bangun dari Tidur mu dan buatlah perubahan untuk Hari ini! \n Have A Nice Day :)"
    }
}