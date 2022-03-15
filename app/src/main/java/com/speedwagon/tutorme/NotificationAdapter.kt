package com.speedwagon.tutorme

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){
    private val image = arrayOf(
        R.drawable.ic_account,
        R.drawable.ic_account,
        R.drawable.ic_account
    )

    private val notify = arrayOf(
        "A user has answered the question you've asked.",
        "A user has answered the question you've asked.",
        "A user has answered the question you've asked."
    )

    private val time = arrayOf(
        "1 hour ago",
        "2 days ago",
        "1 week ago"
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgView : ImageView
        var txtNotify : TextView
        var txtTime : TextView

        init {
            imgView = itemView.findViewById(R.id.imgView)
            txtNotify = itemView.findViewById(R.id.txtNotify)
            txtTime = itemView.findViewById(R.id.txtTime)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
        .inflate(R.layout.recycler_notification,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgView.setImageResource(image.get(position))
        holder.txtNotify.text = notify[position]
        holder.txtTime.text = time[position]

    }

    override fun getItemCount(): Int {
        return image.size
    }
}