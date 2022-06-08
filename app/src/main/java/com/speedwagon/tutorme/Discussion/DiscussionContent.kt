package com.speedwagon.tutorme.Discussion

import android.app.*
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.speedwagon.tutorme.R
import com.speedwagon.tutorme.Service.Object_Service.Constants
import com.speedwagon.tutorme.Service.ServiceOnDiscussion
import com.speedwagon.tutorme.databinding.DiscussionContentBinding
import com.speedwagon.tutorme.home_main
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DiscussionContent: AppCompatActivity() {

    private lateinit var binding:DiscussionContentBinding
    private lateinit var CommentRecyclerView: RecyclerView
    private lateinit var Commentlist : ArrayList<ItemDiscussion>
    private var isback : Int = 0
    var myFirstRun : FirstRunSharePref?= null

    private var mhs = listOf(
        ItemDiscussion("Asep","Halo"),ItemDiscussion("Yanto","Halo juga"),ItemDiscussion("Yono","Hmmm")
        ,ItemDiscussion("SiPalingTahu","Ni diskusi apa sih ?"),ItemDiscussion("Kipli","Bang hatiku kok terasa sepi sekali")
        ,ItemDiscussion("Kiplili","Ga usah ngeBadut"),ItemDiscussion("Zhen","Valo yok ges")
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DiscussionContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StartTheService()

        //recycleview
        CommentRecyclerView = findViewById<RecyclerView>(R.id.RecycleComment)
        Commentlist = arrayListOf()
        CommentRecyclerView?.layoutManager = LinearLayoutManager(this)

        //sql
        myFirstRun = FirstRunSharePref(this)
        if(myFirstRun!!.firstRun){
            val secondIntent = Intent(this, PreLoad::class.java)
            startActivity(secondIntent)
        }
        updateAdapter()

        //Tombol kembali
        binding.Back.setOnClickListener{
            if(isback==1){
                val intent = Intent(this, home_main::class.java)
                StopTheService()
                startActivity(intent)
            }
            else
            {
                StopTheService()
                this.findActivity()?.onBackPressed()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        updateAdapter()
    }

    private fun updateAdapter() {
        doAsync {
            var nameList = CommentTransaction(this@DiscussionContent).viewAllName()
            uiThread {
                CommentRecyclerView.adapter = DiscussionAdapter(ArrayList(nameList))
            }

        }
    }

    //Tombol kembali
    private fun findActivity(): Activity? {
        var context = this
        while(context is ContextWrapper){
            if(context is Activity)
                return context
            context = context.baseContext as DiscussionContent
        }
        return null
    }
    //Tombol kembali

    //membuat notifikasi
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
        stopService(Intent(this,ServiceOnDiscussion::class.java))
    }

    private fun StartTheService() {
        if(isMyserviceRunning(ServiceOnDiscussion::class.java)){
            Toast.makeText(this, "Welcome Back !!!", Toast.LENGTH_SHORT).show()
            isback = 1
        }else{
            startService(Intent(this,ServiceOnDiscussion::class.java))
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


}