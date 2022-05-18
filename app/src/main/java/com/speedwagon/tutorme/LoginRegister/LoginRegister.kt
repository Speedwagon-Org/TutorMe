package com.speedwagon.tutorme.LoginRegister

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.speedwagon.tutorme.R

private val PrefFileName = "MYFILEPREF01"

class LoginRegister : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)
        val Fragment = Login()
        val fm: FragmentManager = supportFragmentManager
        fm.beginTransaction().add(R.id.logResLayout,Fragment).commit()
    }
}