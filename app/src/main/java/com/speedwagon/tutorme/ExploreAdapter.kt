package com.speedwagon.tutorme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExploreAdapter : RecyclerView.Adapter<ExploreAdapter.ViewHolder>(){
    private val image = arrayOf(
        R.drawable.ic_account,
        R.drawable.ic_account,
        R.drawable.ic_account
    )

    private val username = arrayOf(
        "CoolKid42",
        "AlexTheGamer",
        "NoobMaster"
    )

    private val question = arrayOf(
        "How do I open a new fragment from another fragment?",
        "How to make Discord bot?",
        "Best mechanical keyboard under $100"
    )

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
        holder.imgAccount.setImageResource(image.get(position))
        holder.tvUsername.text = username[position]
        holder.tvQuestion.text = question[position]

    }

    override fun getItemCount(): Int {
        return image.size
    }
}