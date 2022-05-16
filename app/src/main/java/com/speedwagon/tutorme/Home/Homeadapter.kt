package com.speedwagon.tutorme.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.speedwagon.tutorme.R

class Homeadapter(
    private val DiscussionList: ArrayList<HomeItem>,
    private val onExploreClickListener: OnHomeClickListener
) : RecyclerView.Adapter<Homeadapter.ViewHolder>(){

    interface OnHomeClickListener{
        fun onHomeClicked(position: Int,item: HomeItem)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgAccount : ImageView
        var tvUsername : TextView
        var tvQuestion : TextView

        init {
            imgAccount = itemView.findViewById(R.id.imgAccount)
            tvUsername = itemView.findViewById(R.id.tvUsername)
            tvQuestion = itemView.findViewById(R.id.tvQuestion)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_home,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemdiscussion = DiscussionList[position]
        holder.apply {
            imgAccount.setImageResource(itemdiscussion.imageprofile)
            tvUsername.text = itemdiscussion.username
            tvQuestion.text = itemdiscussion.text
            itemView.setOnClickListener {
                onExploreClickListener.onHomeClicked(position,itemdiscussion)
            }
        }
    }

    override fun getItemCount(): Int {
        return DiscussionList.size
    }
}