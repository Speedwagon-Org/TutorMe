package com.speedwagon.tutorme.Discussion

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.speedwagon.tutorme.R
import com.speedwagon.tutorme.Service.ServiceOnDiscussion
import com.speedwagon.tutorme.databinding.ActivityMainBinding
import com.speedwagon.tutorme.databinding.DiscussionContentBinding

class DiscussionContent: AppCompatActivity() {
    private lateinit var binding:DiscussionContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DiscussionContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StartTheService()
        binding.Back.setOnClickListener{
            finish()
            StopTheService()
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