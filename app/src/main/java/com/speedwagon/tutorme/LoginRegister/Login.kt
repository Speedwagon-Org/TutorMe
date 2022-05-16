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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.speedwagon.tutorme.R
import com.speedwagon.tutorme.home_main

class Login : Fragment() {

    private  lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_login, container, false)
        val btnToReg = v.findViewById<Button>(R.id.toRegister)
        val btnToHome = v.findViewById<Button>(R.id.buttonLogin)
        

        //check apakah user telah login sebelumnya
        val user = Firebase.auth.currentUser
        if (user != null) {
            val intent = Intent(context, home_main::class.java)
            startActivity(intent)
            activity?.finish()
        }

        //register
        btnToReg.setOnClickListener {
            val registerFragment = Register()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.logResLayout, registerFragment)
            transaction.commit()
        }
        //login
        btnToHome.setOnClickListener {
            val email = v.findViewById<EditText>(R.id.inputEmail).text.toString()
            val password = v.findViewById<EditText>(R.id.inputPassword).text.toString()
            try {

                auth = FirebaseAuth.getInstance()
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val intent = Intent(context, home_main::class.java)
                        startActivity(intent)
                        activity?.finish()

                    }
                }.addOnFailureListener { exception ->
                    view?.findViewById<TextView>(R.id.Loginerror)?.text=exception.localizedMessage
                }
            }catch (e : Exception){
                Toast.makeText(
                    context,
                    "make sure you fill all information needed!!!",
                    Toast.LENGTH_SHORT).show()
            }
        }
        return v
    }

}