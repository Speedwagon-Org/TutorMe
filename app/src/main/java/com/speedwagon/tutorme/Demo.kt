package com.speedwagon.tutorme

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.File

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