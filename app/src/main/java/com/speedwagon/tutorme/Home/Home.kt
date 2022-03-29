package com.speedwagon.tutorme.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.speedwagon.tutorme.Discussion.DiscussionContent
import com.speedwagon.tutorme.Explore.ExploreAdapter
import com.speedwagon.tutorme.Explore.ExploreItem
import com.speedwagon.tutorme.Explore.Homeadapter
import com.speedwagon.tutorme.R

class home : Fragment(), Homeadapter.OnHomeClickListener{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclehome)
        val itemList = createDummy(10)
        recyclerView?.adapter = Homeadapter(itemList,this@home)
        recyclerView?.layoutManager = LinearLayoutManager(context)
    }

    override fun onHomeClicked(position: Int, item: HomeItem) {
        val intent = Intent(context, DiscussionContent::class.java).apply {
            putExtra("HomeItem", item)
        }
        startActivity(intent)
    }

    private fun createDummy (n : Int) : ArrayList<HomeItem>{
        val listOfItem = arrayListOf<HomeItem>()
        for (i in 0..n){
            listOfItem.add(HomeItem(R.drawable.ic_baseline_person_24, "User $i", "This is content"))
        }
        return listOfItem
    }
}