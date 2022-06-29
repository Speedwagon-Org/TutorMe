package com.speedwagon.tutorme.Home

import android.os.Parcelable
import com.speedwagon.tutorme.R
import kotlinx.parcelize.Parcelize
@Parcelize
data class HomeItem (
    var IdUser : String = "null",
    var Idcontent : String = "null",
    var imageprofile: Int = R.drawable.ic_baseline_person_24,
    var username: String = "Another user",
    var text: String = "Jika username nya adalah Another user maka ini adalah discussion user yang lain " +
            ", seharusnya Home tidak ada discussion dari user lain.",
    var countreply:Int = 0,
    var countreplyInString:String = "null"): Parcelable