package com.speedwagon.tutorme.Discussion

import android.app.*
import android.content.ContextWrapper
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.speedwagon.tutorme.Explore.ExploreItem
import com.speedwagon.tutorme.Notification.ItemNotification
import com.speedwagon.tutorme.R
import com.speedwagon.tutorme.databinding.DiscussionContentBinding
import java.io.File

class DiscussionContent: AppCompatActivity() {

    private lateinit var binding:DiscussionContentBinding
    private lateinit var CommentRecyclerView: RecyclerView
    private lateinit var Commentlist : ArrayList<ItemDiscussion>
    private  lateinit var auth: FirebaseAuth
    private lateinit var Database: FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference
    private lateinit var databaseUpload : DatabaseReference
    private lateinit var databaseNotifikasi : DatabaseReference
    private lateinit var databaseContent : DatabaseReference
    private var clicked = false
    private var replycounted: Int = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DiscussionContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set pertanyaan dan pfp
        binding.txt.setText(intent.getStringExtra("Text").toString())
        val imageRef = FirebaseStorage.getInstance().reference.child("pfp_user/user_${intent.getStringExtra("IDuser").toString()}_profile_pict")
        val localFile = File.createTempFile("tempProfileImage", "png")
        imageRef.getFile(localFile).addOnSuccessListener{
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.imgView.setImageBitmap(bitmap)
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
                            if(intent.getStringExtra("IDuser").toString()==it.key){
                                binding.usernameid.text  = userObj["username"] as String
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

        //recycleview
        CommentRecyclerView = findViewById<RecyclerView>(R.id.RecycleComment)
        Commentlist = arrayListOf()
        CommentRecyclerView?.layoutManager = LinearLayoutManager(this)

        //Tombol kembali
        binding.Back.setOnClickListener{
            this.findActivity()?.onBackPressed()
        }
        binding.fab.setOnClickListener {

            clicked = !clicked
            if(clicked){
                binding.sendtextbox.visibility = View.VISIBLE
                binding.fab.setImageResource(R.drawable.ic_baseline_clear_24)
            }
            else{
                binding.sendtextbox.visibility = View.INVISIBLE
                binding.fab.setImageResource(R.drawable.ic_baseline_send_24)
            }
        }

        //kirim jawban
        binding.kirim.setOnClickListener {
            var editText = binding.contentpost
            auth = FirebaseAuth.getInstance()
            Database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")

            //untuk ambil username
            databaseReference = Database.getReference("User/")

            //upload ke database dengan key Content
            databaseUpload = Database.getReference("Comment/${intent.getStringExtra("idcontent").toString()}/")
            databaseNotifikasi = Database.getReference("notifikasi/${intent.getStringExtra("IDuser").toString()}/")

            //check editText tidak kosong
            if(editText.text.toString().isEmpty()){
                Toast.makeText(
                    this,
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
                                    val id = Database.getReference("Comment/${intent.getStringExtra("idcontent").toString()}").push().key
                                    val userData = ItemDiscussion(
                                        IdUser = auth.uid.toString(),
                                        Idcontent = id.toString(),
                                        text = editText.text.toString(),
                                        username = userObj["username"] as String
                                    )
                                    //untuk notifikasi
                                    val notif= ItemNotification(
                                        id = auth.uid.toString(),
                                        Username = userObj["username"] as String
                                    )
                                    databaseUpload.child("$id/").setValue(userData)
                                    //notifikasi
                                    databaseNotifikasi.child("${auth.uid}").setValue(notif)



                                    //kosongkan edittext
                                    editText.text = null

                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }

        CommentRecyclerView = binding.RecycleComment
        Commentlist = arrayListOf()
        CommentRecyclerView?.layoutManager = LinearLayoutManager(this)
        fetchData()

    }

    //Tombol kembali
    private fun findActivity(): Activity? {
        var context = this
        while(context is ContextWrapper){
            if(context is Activity)
                return context
            context = context.baseContext as DiscussionContent
        }
        return null
    }


    private fun fetchData() {
        auth = FirebaseAuth.getInstance()
        Database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
        databaseReference = Database.getReference("Comment/${intent.getStringExtra("idcontent").toString()}/")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    Commentlist.clear()
                    snapshot.children.forEach {
                        val ContentObj = it.value as HashMap<*, *>
                        val categories = ItemDiscussion()
                        with(categories){
                            try {
                                text = ContentObj["text"] as String
                                IdUser = ContentObj["idUser"] as String
                                replycounted += 1
                            } catch (e : Exception){
                                Toast.makeText(this@DiscussionContent, "$e", Toast.LENGTH_SHORT).show()
                            }
                        }
                        Commentlist.add(categories)
                    }
                    auth = FirebaseAuth.getInstance()
                    databaseContent = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Content")
                    databaseContent.child("${intent.getStringExtra("idcontent").toString()}").child("countreply").setValue(replycounted)
                    replycounted = 0
                    CommentRecyclerView.adapter = DiscussionAdapter(Commentlist)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}