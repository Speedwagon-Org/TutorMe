package com.speedwagon.tutorme

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.speedwagon.tutorme.Discussion.DiscussionContent
import com.speedwagon.tutorme.LoginRegister.DataUser
import com.speedwagon.tutorme.databinding.FragmentProfileNavBinding

private const val EXTRA_STATUS= "STATUS_STATE"
private  var someStateValue = "kosong"

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
        return view
    }

    // onSaveInstanceState

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val usernameid= view?.findViewById<TextView>(R.id.usernameid)
        outState.putString(EXTRA_STATUS,usernameid?.text.toString())
    }

}