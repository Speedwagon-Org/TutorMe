package com.speedwagon.tutorme.Discussion

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.speedwagon.tutorme.R
import com.speedwagon.tutorme.Service.Object_Service.Constants
import com.speedwagon.tutorme.Service.ServiceOnDiscussion
import com.speedwagon.tutorme.databinding.DiscussionContentBinding
import com.speedwagon.tutorme.home_main

class DiscussionContent: AppCompatActivity() {
    private lateinit var binding:DiscussionContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DiscussionContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StartTheService()

        //create notification
        createNotificationChannel()

        // Create an explicit intent for an Activity
        val intentreply = Intent(this, DiscussionContent::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intentreply, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, Constants.Channel_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        //show notification
        binding.shownotification.setOnClickListener {
            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(Constants.Notification_ID, builder.build())
            }
        }

        binding.Back.setOnClickListener{
            StopTheService()
            val homeMain = Intent(this, home_main::class.java)
            startActivity(homeMain)
            finish()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText= "Test"
            val serviceChannel = NotificationChannel(
                Constants.Channel_ID,"My Service Channel",
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

    private fun StopTheService() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show()
        stopService(Intent(this,ServiceOnDiscussion::class.java))
    }

    private fun StartTheService() {
        if(isMyserviceRunning(ServiceOnDiscussion::class.java)){
            Toast.makeText(this, "Welcome Back !!!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()
            startService(Intent(this,ServiceOnDiscussion::class.java))
        }
    }

    private fun isMyserviceRunning(mClass: Class<ServiceOnDiscussion>): Boolean {
        val manager: ActivityManager = getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager
        for(service: ActivityManager.RunningServiceInfo in
        manager.getRunningServices(Integer.MAX_VALUE)){
            if(mClass.name.equals(service.service.className)){
                return true
            }
        }
        return false

    }


}