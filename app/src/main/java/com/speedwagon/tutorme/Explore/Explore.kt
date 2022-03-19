package com.speedwagon.tutorme.Explore

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.speedwagon.tutorme.Discussion.DiscussionContent
import com.speedwagon.tutorme.R
class explore : Fragment(), ExploreAdapter.OnExploreClickListener{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }
    //variable untuk implementasi database data
    private lateinit var discussionlist: ArrayList<ExploreItem>
    private lateinit var discussionRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycleExplore)
        val itemList = createDummy(10)
        recyclerView?.adapter = ExploreAdapter(itemList,this@explore)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        }

    override fun onExploreClicked(position: Int, item: ExploreItem) {
        Toast.makeText(context, "starting activity in $position", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, DiscussionContent::class.java).apply {
            putExtra("ExploreItem", item)
        }
        startActivity(intent)
    }
    private fun createDummy (n : Int) : ArrayList<ExploreItem>{
        val listOfItem = arrayListOf<ExploreItem>()
        for (i in 0..n){
            listOfItem.add(ExploreItem(R.drawable.ic_baseline_person_24, "User $i", "This is content"))
        }
        return listOfItem
    }

}