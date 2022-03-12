package com.speedwagon.tutorme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import kotlinx.android.synthetic.main.activity_home.*

class home_main : AppCompatActivity() {

    private val home = home()
    private val Explore = explore()
    private val create = Create_discussion()
    private val notification = notification()
    private val profile = profile_nav()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        replacefragment(home)

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
}