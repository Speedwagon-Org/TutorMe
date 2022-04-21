package com.speedwagon.tutorme.Explore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.speedwagon.tutorme.Discussion.DiscussionContent
import com.speedwagon.tutorme.Discussion.ItemDiscussion
import com.speedwagon.tutorme.R

class explore : Fragment(), ExploreAdapter.OnExploreClickListener{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }
    //variable untuk implementasi database data
    private lateinit var discussionlist: ArrayList<ExploreItem>
    private lateinit var discussionRecyclerView: RecyclerView
    private lateinit var database  : DatabaseReference


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = Intent(context,explore::class.java)
        val DiscussionDetail = intent.getParcelableExtra<ItemDiscussion>("ItemDiscussion")

        discussionRecyclerView = view?.findViewById<RecyclerView>(R.id.recycleExplore)
        discussionlist = arrayListOf()
        discussionRecyclerView?.layoutManager = LinearLayoutManager(context)
        fetchData()
        }
    //fetchdata dari firebase
    private fun fetchData() {
        database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Content")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    discussionlist.clear()
                    snapshot.children.forEach {
                        val ContentObj = it.value as HashMap<*, *>
                        val categories = ExploreItem()
                        with(categories){
                            try {
                                username = ContentObj["username"] as String
                                text = ContentObj["text"] as String
                            } catch (e : Exception){
                                Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
                            }
                        }
                        discussionlist.add(categories)
                    }
                   discussionRecyclerView.adapter = ExploreAdapter(discussionlist, this@explore)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onExploreClicked(position: Int, item: ExploreItem) {
        val intent = Intent(context, DiscussionContent::class.java).apply {
            putExtra("ExploreItem", item)
        }
        startActivity(intent)
    }



    //Loader

}