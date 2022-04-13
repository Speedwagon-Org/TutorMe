package com.speedwagon.tutorme.Service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.speedwagon.tutorme.Discussion.DiscussionContent
import com.speedwagon.tutorme.R
import com.speedwagon.tutorme.Service.Object_Service.Constants.Channel_ID
import com.speedwagon.tutorme.Service.Object_Service.Constants.Notification_ID

class ServiceOnDiscussion: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    override fun onCreate(){
        super.onCreate()
        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ShowNotification()
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun ShowNotification() {
        val notificationIntent = Intent(this, DiscussionContent::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = TaskStackBuilder.create(this)
            .addNextIntentWithParentStack(notificationIntent)
            .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = Notification
            .Builder(this, Channel_ID)
            .setContentText("Ayo Kembali Ke Diskusi Mu!!!")
            .setSmallIcon(R.drawable.logo)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(Notification_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText= "Test"
            val serviceChannel = NotificationChannel(
                Channel_ID,"My Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                setShowBadge(true)
                description = descriptionText
            }
            val manager = getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(serviceChannel)
        }
    }
}