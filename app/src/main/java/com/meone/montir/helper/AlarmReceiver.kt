package com.meone.montir.helper

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.meone.montir.helper.alarmNotification.RunnerNotifier

//class AlarmReceiver : BroadcastReceiver() {
//    @SuppressLint("UnspecifiedImmutableFlag", "MissingPermission")
//    override fun onReceive(context: Context, intent: Intent?) {
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
//
//            val i = Intent(context, StopTrackerActivity::class.java)
//            intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//            val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)
//
//            val builder =
//                NotificationCompat.Builder(context!!, "Montir").setSmallIcon(R.drawable.brand_logo)
//                    .setContentTitle("MONTIR: Alarm Notification")
//                    .setContentText("Matikan Alarm jika Anda sudah Bangun dari Tidur anda!")
//                    .setAutoCancel(true)
//                    .setDefaults(NotificationCompat.DEFAULT_ALL)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .setContentIntent(pendingIntent)
//
//            val notificationManager = NotificationManagerCompat.from(context)
//            notificationManager.notify(123, builder.build())
//        }
//
//    }
//}

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notificationManager =
                it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val runnerNotifier = RunnerNotifier(notificationManager, it)
            runnerNotifier.showNotification()
        }
    }
}
