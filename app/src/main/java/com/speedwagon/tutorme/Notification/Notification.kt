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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycleNotify)
        val itemList = createDummy(10)
        recyclerView?.adapter = NotificationAdapter(itemList,this@notification)
        recyclerView?.layoutManager = LinearLayoutManager(context)
    }

    override fun onNotifClicked(position: Int, item: ItemNotification) {
        Toast.makeText(context, "starting activity in $position", Toast.LENGTH_SHORT).show()
    }
    private fun createDummy (n : Int) : ArrayList<ItemNotification>{
        val listOfItem = arrayListOf<ItemNotification>()
        for (i in 0..n){
            listOfItem.add(ItemNotification(R.drawable.ic_baseline_person_24, "User $i"))
        }
        return listOfItem
    }
}