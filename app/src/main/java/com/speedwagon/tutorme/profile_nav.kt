package com.speedwagon.tutorme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val EXTRA_STATUS= "STATUS_STATE"
private var someStateValue = "kosong"
private var clicked = false

class profile_nav : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //onSaveinstance
        val view = inflater.inflate(R.layout.fragment_profile_nav, container, false)
        val textView: TextView = view.findViewById(R.id.usernameid)
        textView.text = savedInstanceState?.getString(EXTRA_STATUS)

        val args = this.arguments
        val inputdata = args?.get("data")
        textView.text = inputdata.toString()

        val btn = view.findViewById<Button>(R.id.UpdateProfileBtn)
        btn.setOnClickListener {
            val currentUsername: TextView = view.findViewById(R.id.usernameid)
            val input = currentUsername.text.toString()
            val bundle = Bundle()
            bundle.putString("data", input)
            val fragment = UpdateProfileFragment()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView, fragment)
                ?.commit()
        }
        //Floating action button
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        val remind = view.findViewById<FloatingActionButton>(R.id.fab_remind)
        fab.setOnClickListener {
            clicked = !clicked
            if(!clicked){
                remind.visibility = View.VISIBLE
            }
            else{
                remind.visibility = View.INVISIBLE
            }
        }
        remind.setOnClickListener{
            val remindMe = RemindMe()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentContainerView,remindMe)?.commit()
        }
        return view
    }

    // onSaveInstanceState

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val usernameid= view?.findViewById<TextView>(R.id.usernameid)
        outState.putString(EXTRA_STATUS,usernameid?.text.toString())
    }
}