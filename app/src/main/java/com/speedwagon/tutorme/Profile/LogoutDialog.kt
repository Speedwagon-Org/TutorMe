package com.speedwagon.tutorme

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.speedwagon.tutorme.LoginRegister.LoginRegister

class LogoutDialog : DialogFragment() {
    private  lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logout_dialog, container, false)
        var confirm = view.findViewById<Button>(R.id.confirm)

        confirm?.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            auth.signOut()
            val intent = Intent(context, LoginRegister::class.java)
            startActivity(intent)

        }

        return view

    }

}