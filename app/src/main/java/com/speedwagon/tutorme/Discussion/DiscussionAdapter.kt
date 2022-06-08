package com.speedwagon.tutorme.Discussion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.speedwagon.tutorme.Notification.ItemNotification
import com.speedwagon.tutorme.R

class DiscussionAdapter (private val CommentList: ArrayList<ItemDiscussion>
    ) : RecyclerView.Adapter<DiscussionAdapter.ViewHolder>(){

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            var imgView : ImageView
            var Username : TextView
            var CommentUSer : TextView

            init {
                imgView = itemView.findViewById(R.id.imgView)
                Username = itemView.findViewById(R.id.usernameid)
                CommentUSer = itemView.findViewById(R.id.txt)
            }
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.recycler_notification,viewGroup,false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val commentdiscussion = CommentList[position]
            holder.imgView.setImageResource(commentdiscussion.ProfilePicture!!)
            holder.Username.text = commentdiscussion.username
            holder.CommentUSer.text = commentdiscussion.Text
        }

        override fun getItemCount(): Int {
            return CommentList.size
        }
}