package com.speedwagon.tutorme

import android.annotation.TargetApi
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.speedwagon.tutorme.Discussion.ItemDiscussion
import com.speedwagon.tutorme.Explore.ExploreItem
import com.speedwagon.tutorme.LoginRegister.DataUser
import com.speedwagon.tutorme.LoginRegister.Login

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
            Database =
                FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
            //untuk ambil username
            databaseReference = Database.getReference("User/")
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
                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            if (snapshot.exists()) {
                                val userObj = it.value as HashMap<*, *>
                                if (auth.currentUser!!.uid == it.key) {
                                    val userData = ExploreItem(
                                        IdUser = auth.uid.toString(),
                                        text = editText?.text.toString(),
                                        username = userObj["username"] as String
                                    )
                                    val id = Database.getReference("Content").push().key
                                    databaseUpload.child("$id/").setValue(userData)

                                    //kosongkan edittext
                                    editText?.text = null

                                    val dialog = SuccesfullySend()
                                    dialog.show(parentFragmentManager, "Succes")
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }

        //Suara yang keluar apabila tombol kirim di klik
        if(soundId != null)
            sp?.play(soundId,1.0f,1.0f,0,0,1.0f)
        }
        return view
    }
}
