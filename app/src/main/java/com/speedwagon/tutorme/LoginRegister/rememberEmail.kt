package com.speedwagon.tutorme.LoginRegister

import android.content.Context
import android.content.SharedPreferences

class rememberEmail(context: Context, name: String){
    val USER_EMAIL = "EMAIL"
    private var myPreferences : SharedPreferences
    init{
        myPreferences =  context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.commit()
    }
    var email : String
        get() = myPreferences.getString(USER_EMAIL, "test").toString()
        set(value) {
            myPreferences.editMe {
                it.putString(USER_EMAIL, value)
            }
        }
}