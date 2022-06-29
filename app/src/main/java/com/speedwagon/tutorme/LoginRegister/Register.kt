package com.speedwagon.tutorme.LoginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.speedwagon.tutorme.Discussion.DiscussionContent
import com.speedwagon.tutorme.Home.home
import com.speedwagon.tutorme.R
import com.speedwagon.tutorme.home_main

class Register : Fragment() {

    private lateinit var Database: FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference
    private  lateinit var auth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnToRegister = view.findViewById<Button>(R.id.buttonRegister)
        val btnToLog = view.findViewById<Button>(R.id.toLogin)

        //input ke database// menadaftarkan user baru
        btnToRegister.setOnClickListener {
            Database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
            databaseReference = Database.getReference("User/")
            val password = view.findViewById<EditText>(R.id.inputPassword).text.toString()
            val email = view.findViewById<EditText>(R.id.inputEmail).text.toString()
            try {
                auth = FirebaseAuth.getInstance()
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        //menyimpan data user ke realtime database
                        val userData = DataUser(
                            username = view?.findViewById<EditText>(R.id.inputUsername)?.text.toString())
                        databaseReference.child("${auth.uid}/").setValue(userData)
                        //tampilkan toast berhasil register
                        Toast.makeText(
                            context,
                            "Your account has been created",
                            Toast.LENGTH_SHORT).show()

                        //user auto login
                        val intent = Intent(context, home_main::class.java)
                        startActivity(intent)
                    }
                }.addOnFailureListener {exception->
                    view.findViewById<TextView>(R.id.errorRegister).text=exception.localizedMessage
                }
            }catch (e :Exception){
                Toast.makeText(
                    context,
                    "make sure you fill all information needed!!!",
                    Toast.LENGTH_SHORT).show()
            }

        }

        //tombol menuju fragment login
        btnToLog.setOnClickListener{
            val loginFragment = Login()
            val transaction : FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.logResLayout,loginFragment)
            transaction.commit()
        }
    }
}
