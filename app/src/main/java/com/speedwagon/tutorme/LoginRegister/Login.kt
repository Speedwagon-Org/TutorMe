package com.speedwagon.tutorme.LoginRegister

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.speedwagon.tutorme.R
import com.speedwagon.tutorme.home_main
import com.speedwagon.tutorme.profile_nav

class Login : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_login, container, false)
        val btnToReg = v.findViewById<Button>(R.id.toRegister)
        val btnToHome = v.findViewById<Button>(R.id.buttonLogin)
        //register
        btnToReg.setOnClickListener {
            val registerFragment = Register()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.logResLayout, registerFragment)
            transaction.commit()
        }
        //login
        btnToHome.setOnClickListener {
            val username = v.findViewById<EditText>(R.id.inputUsername).text.toString()
            val password = v.findViewById<EditText>(R.id.inputPassword).text.toString()
            verification(Username = username, UserPassword = password)
        }
        return v
    }

    //mengecheck data user ada tau tidak
    private fun verification(Username: String? = null, UserPassword: String? = null) {
        if (!Username.isNullOrEmpty() && !UserPassword.isNullOrEmpty()) {
            val database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User/")
            database.addValueEventListener(object : ValueEventListener {
                var FoundData = false
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val userObj = it.value as HashMap<*, *>
                        println("userObj : ${it.key.toString()}")
                        if ((userObj["username"] as String).uppercase() == Username.uppercase() && userObj["pass"] as String == UserPassword) {
                            val intent = Intent(context, home_main::class.java)
                            FoundData = true
                            startActivity(intent)
                        }
                    }
                    //jika username tidak ditemukan
                    if (FoundData == false) {
                        Toast.makeText(
                            context,
                            "Username doesn't exist or Password invalid",
                            Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }else {
            Toast.makeText(context, "username or password is still empty", Toast.LENGTH_SHORT)
                .show()
        }
    }

}