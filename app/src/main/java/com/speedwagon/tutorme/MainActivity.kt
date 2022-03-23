package com.speedwagon.tutorme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import com.speedwagon.tutorme.Receiver.InternetReceiver

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