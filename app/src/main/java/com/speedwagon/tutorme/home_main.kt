package com.speedwagon.tutorme

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.speedwagon.tutorme.Explore.explore
import com.speedwagon.tutorme.Home.home
import com.speedwagon.tutorme.Notification.notification
import com.speedwagon.tutorme.Profile.profile_nav
import com.speedwagon.tutorme.Receiver.InternetReceiver
import com.speedwagon.tutorme.Service.ServiceOnDiscussion

class home_main : AppCompatActivity(), InternetReceiver.ConnectionReceiverListener{
    private val home = home()
    private val Explore = explore()
    private val create = Create_discussion()
    private val notification = notification()
    private val profile = profile_nav()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //internet connection
        baseContext.registerReceiver(InternetReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        com.speedwagon.tutorme.application.instance.setConnectionListener(this)
        //
        setContentView(R.layout.activity_home)
        replacefragment(home)
        NAvbar()
        StartTheService()
    }

    private fun NAvbar(){
        val bottomnav= findViewById<BottomNavigationView>(R.id.bottomnav)

        bottomnav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home2 -> replacefragment(home)
                R.id.explore -> replacefragment(Explore)
                R.id.create_discussion -> replacefragment(create)
                R.id.notification_frag -> replacefragment(notification)
                R.id.profile_nav -> replacefragment(profile)
            }
            true
        }
    }

    private fun StartTheService() {
        if(isMyserviceRunning(ServiceOnDiscussion::class.java)){
            Toast.makeText(this, "Welcome Back !!!", Toast.LENGTH_SHORT).show()
            stopService(Intent(this, ServiceOnDiscussion::class.java))
        }else{
            startService(Intent(this, ServiceOnDiscussion::class.java))
        }
    }

    //check service berjalan atau tidak
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

    private  fun replacefragment(fragment: Fragment){
        if(fragment !=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, fragment)
            transaction.commit()
        }
    }
    override fun onNetworkConnectionChange(isConnected: Any){
        if (isConnected as Boolean){
            Toast.makeText(this, "Connect", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "Not Connect", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        NAvbar()
    }
}