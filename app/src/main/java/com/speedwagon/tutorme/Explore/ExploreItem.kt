package com.speedwagon.tutorme.Explore

import android.os.Parcelable
import com.speedwagon.tutorme.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExploreItem(
    var IdUser: String = "null",
    var Idcontent : String = "null",
    var imageprofile: Int = R.drawable.ic_baseline_person_24,
    var username: String = "Guest",
    var text: String = "test",
    var countreply:Int = 0,
    var countreplyInString:String = "null"): Parcelable