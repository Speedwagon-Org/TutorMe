package com.speedwagon.tutorme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.speedwagon.tutorme.LoginRegister.LoginRegister

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler(Looper.getMainLooper()).postDelayed({
            val logInIntent = Intent(this@MainActivity, LoginRegister::class.java)
            startActivity(logInIntent)
        }, 400)
    }
}