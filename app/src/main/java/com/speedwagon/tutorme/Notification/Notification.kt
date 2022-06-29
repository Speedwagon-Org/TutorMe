package com.speedwagon.tutorme.Notification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.speedwagon.tutorme.Discussion.DiscussionContent
import com.speedwagon.tutorme.Explore.ExploreAdapter
import com.speedwagon.tutorme.Explore.ExploreItem
import com.speedwagon.tutorme.R


class notification : Fragment(),NotificationAdapter.OnNotifClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       return inflater.inflate(R.layout.fragment_notification, container, false)
    }
    //variable untuk implementasi database data
    private lateinit var notificationlist: ArrayList<ItemNotification>
    private lateinit var notificationRecyclerView: RecyclerView
    private lateinit var database  : FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationRecyclerView = view?.findViewById<RecyclerView>(R.id.recycleNotify)
        notificationlist = arrayListOf()
        notificationRecyclerView.adapter = NotificationAdapter(notificationlist,this@notification)
        notificationRecyclerView.layoutManager = LinearLayoutManager(context)
        fetch()
    }

    private fun fetch() {

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
        databaseReference = database.getReference("notifikasi/${auth.uid}")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    notificationlist.clear()
                    snapshot.children.forEach {
                        val ContentObj = it.value as HashMap<*, *>
                        val categories = ItemNotification()
                        with(categories){
                            try {
                                Username = ContentObj["username"] as String
                                id = ContentObj["id"] as String
                                println("data notifikasi : "+Username+", "+id)

                            } catch (e : Exception){
                                Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
                            }
                        }
                        notificationlist.add(categories)
                    }
                    notificationRecyclerView.adapter = NotificationAdapter(notificationlist, this@notification)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onNotifClicked(position: Int, item: ItemNotification) {
        Toast.makeText(context, "starting activity in $position", Toast.LENGTH_SHORT).show()
    }



}