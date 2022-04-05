package com.speedwagon.tutorme.Receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.speedwagon.tutorme.R

const val EXTRA_PESAN: String = "EXTRA_PESAN"
class RemindReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notifyId = 30103
        val channelId = "my_channel_01"
        val name = "ON/OFF"
        val importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_HIGH
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val mNotifyChannel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(channelId,name, importance)
        } else {
            return
        }
        val mBuilder = NotificationCompat.Builder(context!!,channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentText(intent?.getStringExtra(EXTRA_PESAN))
            .setContentTitle("Remind Me")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        var mNotificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager
        for (s in mNotificationManager.notificationChannels){
            mNotificationManager.deleteNotificationChannel(s.id)
        }
        mNotificationManager.createNotificationChannel(mNotifyChannel)
        mNotificationManager.notify(notifyId,mBuilder.build())
    }
}