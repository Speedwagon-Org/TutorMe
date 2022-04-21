package com.speedwagon.tutorme.Explore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.speedwagon.tutorme.R

class ExploreAdapter(
    private val DiscussionList: ArrayList<ExploreItem>,
    private val onExploreClickListener: OnExploreClickListener
    ) : RecyclerView.Adapter<ExploreAdapter.ViewHolder>(){

    interface OnExploreClickListener{
        fun onExploreClicked(position: Int,item: ExploreItem)
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
            .inflate(R.layout.recycler_explore,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemdiscussion = DiscussionList[position]
        holder.imgAccount.setImageResource(itemdiscussion.imageprofile)
        holder.tvUsername.text = itemdiscussion.username
        holder.tvQuestion.text = itemdiscussion.text
        holder.itemView.setOnClickListener {
            onExploreClickListener.onExploreClicked(position,itemdiscussion)
        }
    }

    override fun getItemCount(): Int {
        return DiscussionList.size
    }
}