package com.speedwagon.tutorme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.speedwagon.tutorme.Discussion.ItemDiscussion
import com.speedwagon.tutorme.LoginRegister.DataUser
import com.speedwagon.tutorme.LoginRegister.Login

class Create_discussion : Fragment() {

    private lateinit var Database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_discussion, container, false)
    }


}
