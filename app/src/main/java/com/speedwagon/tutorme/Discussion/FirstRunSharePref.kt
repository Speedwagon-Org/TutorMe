package com.speedwagon.tutorme.Discussion

import android.content.Context
import android.content.SharedPreferences

class FirstRunSharePref(context: Context) {
    private val keyPref = "FIRST_RUN"
    private var myPreferences : SharedPreferences
    init{
        myPreferences = context.getSharedPreferences("sharePrefKey",Context.MODE_PRIVATE)
    }
    var firstRun : Boolean
        get() = myPreferences.getBoolean(keyPref,true)
        set(value) {
            myPreferences.edit().putBoolean(keyPref,value).commit()
        }
}