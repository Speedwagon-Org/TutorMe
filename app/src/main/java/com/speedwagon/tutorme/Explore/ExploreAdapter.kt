package com.speedwagon.tutorme.Explore

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.speedwagon.tutorme.R
import java.io.File

class ExploreAdapter(
    private val DiscussionList: ArrayList<ExploreItem>,
    private val onExploreClickListener: OnExploreClickListener
    ) : RecyclerView.Adapter<ExploreAdapter.ViewHolder>(){

    private lateinit var Database: FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference

    interface OnExploreClickListener{
        fun onExploreClicked(position: Int,item: ExploreItem)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgAccount : ImageView
        var tvUsername : TextView
        var tvQuestion : TextView
        var countreply : TextView

        init {
            imgAccount = itemView.findViewById(R.id.imgAccount)
            tvUsername = itemView.findViewById(R.id.tvUsername)
            tvQuestion = itemView.findViewById(R.id.tvQuestion)
            countreply = itemView.findViewById(R.id.Countreply)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_explore,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemdiscussion = DiscussionList[position]

        val imageRef = FirebaseStorage.getInstance().reference.child("pfp_user/user_${itemdiscussion.IdUser}_profile_pict")
        val localFile = File.createTempFile("tempProfileImage", "png")
        imageRef.getFile(localFile).addOnSuccessListener{
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            holder.imgAccount.setImageBitmap(bitmap)
        }.addOnFailureListener{ e->
        }


        //set username
        Database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
        databaseReference = Database.getReference("User/")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    if(snapshot.exists()){
                        val userObj = it.value as HashMap <*,*>
                        try {
                            if(itemdiscussion.IdUser==it.key){
                                holder.tvUsername.text  = userObj["username"] as String
                            }
                        }catch (e: java.lang.Exception){
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        holder.tvQuestion.text = itemdiscussion.text
        holder.itemView.setOnClickListener {
            onExploreClickListener.onExploreClicked(position,itemdiscussion)
        }

        holder.countreply.text = itemdiscussion.countreplyInString
    }

    override fun getItemCount(): Int {
        return DiscussionList.size
    }
}