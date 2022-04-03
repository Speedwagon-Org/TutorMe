package com.speedwagon.tutorme.LoginRegister

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataUser (
    var username : String? = null,
    val email : String? = null,
    val pass : String? = null):Parcelable