package com.speedwagon.tutorme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Demo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        findViewById<Button>(R.id.toMainMenu).setOnClickListener {
            val MainmenuIntent = Intent(this, home_main::class.java)
            startActivity(MainmenuIntent)
        }
    }
}