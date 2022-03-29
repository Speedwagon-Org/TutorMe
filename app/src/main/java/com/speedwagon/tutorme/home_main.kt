package com.speedwagon.tutorme

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.speedwagon.tutorme.Explore.explore
import com.speedwagon.tutorme.Home.home
import com.speedwagon.tutorme.Notification.notification
import com.speedwagon.tutorme.Receiver.InternetReceiver

class home_main : AppCompatActivity(), InternetReceiver.ConnectionReceiverListener{

    private val home = home()
    private val Explore = explore()
    private val create = Create_discussion()
    private val notification = notification()
    private val profile = profile_nav()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseContext.registerReceiver(InternetReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        com.speedwagon.tutorme.application.instance.setConnectionListener(this)
        setContentView(R.layout.activity_home)
        replacefragment(home)

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
}