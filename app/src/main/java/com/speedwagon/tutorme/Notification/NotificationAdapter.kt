package com.speedwagon.tutorme.Notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.speedwagon.tutorme.Explore.ExploreAdapter
import com.speedwagon.tutorme.Explore.ExploreItem
import com.speedwagon.tutorme.R


class NotificationAdapter(
    private val NotificationList: ArrayList<ItemNotification>,
    private val onNotifClickListener:OnNotifClickListener
    ) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){

    interface OnNotifClickListener{
        fun onNotifClicked(position: Int,item: ItemNotification)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgView : ImageView
        var Username : TextView

        init {
            imgView = itemView.findViewById(R.id.imgView)
            Username = itemView.findViewById(R.id.usernameid)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
        .inflate(R.layout.recycler_notification,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemnotification = NotificationList[position]
        holder.imgView.setImageResource(itemnotification.imageprofile)
        holder.Username.text = itemnotification.Username
    }

    override fun getItemCount(): Int {
        return NotificationList.size
    }
}