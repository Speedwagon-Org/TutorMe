package com.speedwagon.tutorme.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.speedwagon.tutorme.Discussion.DiscussionContent
import com.speedwagon.tutorme.R

class home : Fragment(), Homeadapter.OnHomeClickListener{

    private lateinit var Homeitem: ArrayList<HomeItem>
    private lateinit var discussionRecyclerView: RecyclerView
    private lateinit var database  : FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        discussionRecyclerView = view?.findViewById<RecyclerView>(R.id.recyclehome)
        Homeitem = arrayListOf()
        discussionRecyclerView?.layoutManager = LinearLayoutManager(context)
        fetchData()
    }

    private fun fetchData() {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
        databaseReference = database.getReference("Content")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    Homeitem.clear()
                    println(Homeitem)
                    snapshot.children.forEach {
                        val contentObj = it.value as HashMap<*, *>
                        val categories = HomeItem()
                        with(categories){
                            try {
                                if(contentObj["idUser"] == auth.uid.toString()){
                                    text = contentObj["text"] as String
                                    IdUser = contentObj["idUser"] as String
                                    Idcontent = contentObj["idcontent"] as String
                                    countreplyInString = contentObj["countreply"].toString()
                                    Homeitem.add(categories)
                                }
                            } catch (e : Exception){
                                Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    discussionRecyclerView.adapter = Homeadapter(Homeitem, this@home)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onHomeClicked(position: Int, item: HomeItem) {
        val intent = Intent(context, DiscussionContent::class.java).apply {
            putExtra("IDuser", item.IdUser)
            putExtra("Text", item.text)
            putExtra("idcontent", item.Idcontent)
        }
        startActivity(intent)
    }
}