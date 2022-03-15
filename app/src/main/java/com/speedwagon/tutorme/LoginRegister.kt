package com.speedwagon.tutorme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager

class LoginRegister : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        val registerFragment = Register()
        val fm: FragmentManager = supportFragmentManager
        fm.beginTransaction().add(R.id.logResLayout,registerFragment).commit()
    }
}