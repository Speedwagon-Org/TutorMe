package com.speedwagon.tutorme.Discussion

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.speedwagon.tutorme.Notification.ItemNotification
import com.speedwagon.tutorme.R
import java.io.File

class DiscussionAdapter (private val CommentList: ArrayList<ItemDiscussion>
    ) : RecyclerView.Adapter<DiscussionAdapter.ViewHolder>(){

    private lateinit var Database: FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference

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
                .inflate(R.layout.recycleview_comment,viewGroup,false)

            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val commentdiscussion = CommentList[position]

            val imageRef = FirebaseStorage.getInstance().reference.child("pfp_user/user_${commentdiscussion.IdUser}_profile_pict")
            val localFile = File.createTempFile("tempProfileImage", "png")
            imageRef.getFile(localFile).addOnSuccessListener{
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                holder.imgView.setImageBitmap(bitmap)
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
                                if(commentdiscussion.IdUser==it.key){
                                    holder.Username.text  = userObj["username"] as String
                                    println(commentdiscussion.IdUser)
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

            holder.imgView.setImageResource(commentdiscussion.imageprofile!!)
            holder.CommentUSer.text = commentdiscussion.text

        }

        override fun getItemCount(): Int {
            return CommentList.size
        }
}