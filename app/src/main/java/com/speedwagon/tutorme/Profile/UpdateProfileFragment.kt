package com.speedwagon.tutorme.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.speedwagon.tutorme.Discussion.ItemDiscussion
import com.speedwagon.tutorme.LoginRegister.DataUser
import com.speedwagon.tutorme.LoginRegister.Login
import com.speedwagon.tutorme.R

class UpdateProfileFragment : Fragment() {

    private  lateinit var auth: FirebaseAuth
    private lateinit var Database: FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_profile, container, false)
        val textView : TextView = view.findViewById(R.id.currentname)

        val args = this.arguments
        val inputdata = args?.get("data")
        textView.text = inputdata.toString()

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,profile_nav())?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        //update nama
        val btn : Button = view.findViewById(R.id.ConfirmUpdate)
        btn.setOnClickListener{
            FetchDataUser()
            view.findViewById<TextView>(R.id.currentname).text = view.findViewById<EditText>(R.id.UsernameUpdateEditText).text
            Toast.makeText(
                context,
                "Your username has been change",
                Toast.LENGTH_SHORT).show()
        }
        //test Espresso
        view.findViewById<Button>(R.id.Test).setOnClickListener {
            view.findViewById<TextView>(R.id.currentname).text = view.findViewById<EditText>(R.id.UsernameUpdateEditText).text
        }

        return view
    }

    private fun FetchDataUser(){
        val editText = view?.findViewById<EditText>(R.id.UsernameUpdateEditText)?.text.toString()
        auth = FirebaseAuth.getInstance()
        Database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
        databaseReference = Database.getReference("Content/")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val content = ItemDiscussion()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}