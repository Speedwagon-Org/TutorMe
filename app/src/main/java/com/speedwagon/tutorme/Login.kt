package com.speedwagon.tutorme

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction

class Login : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_login, container, false)
        val btnToReg = v.findViewById<Button>(R.id.toRegister)
        val btnToHome = v.findViewById<Button>(R.id.buttonLogin)

        btnToReg.setOnClickListener {
            val registerFragment = Register()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.logResLayout, registerFragment)
            transaction.commit()
        }
        btnToHome.setOnClickListener {
            val intent = Intent(context,home_main::class.java)
            startActivity(intent)
        }
        return v
    }
}