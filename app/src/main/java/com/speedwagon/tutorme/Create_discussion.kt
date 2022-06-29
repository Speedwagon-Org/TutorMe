package com.speedwagon.tutorme

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.SoundPool
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.speedwagon.tutorme.Explore.ExploreItem
import java.io.File

class Create_discussion : Fragment() {

    private  lateinit var auth: FirebaseAuth
    private lateinit var Database: FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference
    private lateinit var databaseUpload : DatabaseReference
    private var sp : SoundPool? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_discussion, container, false)
        val send = view.findViewById<Button>(R.id.Send)

        //Pembuatan soundpool
        fun createSoundPool(){
            sp = SoundPool.Builder().setMaxStreams(15).build()
        }
        createSoundPool()
        val soundId = sp?.load(context,R.raw.posted,1)

        //post pertanyaan
        send.setOnClickListener {
            var editText = view?.findViewById<EditText>(R.id.contentpost)
            auth = FirebaseAuth.getInstance()
            Database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")

            //upload ke database dengan key Content
            databaseUpload = Database.getReference("Content/")

            //check editText tidak kosong
            if(editText?.text.toString().isEmpty()){
                Toast.makeText(
                    context,
                    "make sure you fill all information needed!!!",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                val id = Database.getReference("Content").push().key
                val userData = ExploreItem(
                    IdUser = auth.uid.toString(),
                    Idcontent = id.toString(),
                    text = editText?.text.toString(),
                    countreply = 0
                )
                databaseUpload.child("$id/").setValue(userData)

                //kosongkan edittext
                editText?.text = null

                val dialog = SuccesfullySend()
                dialog.show(parentFragmentManager, "Succes")
            }

        //Suara yang keluar apabila tombol kirim di klik
        if(soundId != null)
            sp?.play(soundId,1.0f,1.0f,0,0,1.0f)
        }
        return view
    }
}
