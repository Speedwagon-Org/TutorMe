package com.speedwagon.tutorme.Profile

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.speedwagon.tutorme.LoginRegister.LoginRegister
import com.speedwagon.tutorme.LoginRegister.Register
import com.speedwagon.tutorme.LogoutDialog
import com.speedwagon.tutorme.R
import com.speedwagon.tutorme.RemindMe
import com.speedwagon.tutorme.home_main

private const val EXTRA_STATUS= "STATUS_STATE"
private var someStateValue = "kosong"
private var clicked = false

class profile_nav : Fragment() {

    private  lateinit var auth: FirebaseAuth
    private lateinit var Database: FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //username dari firebase
        FetchDataUser()

        //onSaveinstance
        val view = inflater.inflate(R.layout.fragment_profile_nav, container, false)
        val textView: TextView = view.findViewById(R.id.usernameid)
        textView.text = savedInstanceState?.getString(EXTRA_STATUS)

        //Floating action button
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        val remind = view.findViewById<FloatingActionButton>(R.id.fab_remind)
        val logout = view.findViewById<FloatingActionButton>(R.id.LogOut)
        fab.setOnClickListener {
            clicked = !clicked
            if(clicked){
                remind.visibility = View.VISIBLE
                logout.visibility = View.VISIBLE
            }
            else{
                remind.visibility = View.INVISIBLE
                logout.visibility = View.INVISIBLE
            }
        }
        remind.setOnClickListener{
            val remindMe = RemindMe()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentContainerView,remindMe)?.commit()
        }
        logout.setOnClickListener{
            val dialog = LogoutDialog()
            dialog.show(parentFragmentManager,"dialogLogut")
        }
        return view
    }

    // onSaveInstanceState

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val usernameid= view?.findViewById<TextView>(R.id.usernameid)
        outState.putString(EXTRA_STATUS,usernameid?.text.toString())
    }

    private fun FetchDataUser(){
        auth = FirebaseAuth.getInstance()
        Database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
        databaseReference = Database.getReference("User/")
    }
}