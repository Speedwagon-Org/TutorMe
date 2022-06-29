package com.speedwagon.tutorme.Profile

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.speedwagon.tutorme.R
import java.io.File

class ProfileAdapater(private val SertifikatList: ArrayList<profileitem>,
                      private val onprofileClickListener: OnprofileClickListener
): RecyclerView.Adapter<ProfileAdapater.ViewHolder>(){

    private lateinit var auth: FirebaseAuth

    interface OnprofileClickListener{
        fun onprofileClicked(position: Int,item: profileitem)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgView : ImageView

        init {
            imgView = itemView.findViewById(R.id.gambarsertifikat)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_sertifikat,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemprofile = SertifikatList[position]
        auth = FirebaseAuth.getInstance()
        println("id sertifikat adapter : ${itemprofile.idimage}")
        val imageRef = FirebaseStorage.getInstance().reference.child("sertifikat/${itemprofile.idimage}")
        val localFile = File.createTempFile("tempProfileImage", "png")
        imageRef.getFile(localFile).addOnSuccessListener{
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            holder.imgView.setImageBitmap(bitmap)
        }.addOnFailureListener{ e->
        }

        holder.itemView.setOnClickListener {
            onprofileClickListener.onprofileClicked(position,itemprofile)
        }
    }

    override fun getItemCount(): Int {
        return SertifikatList.size
    }
}