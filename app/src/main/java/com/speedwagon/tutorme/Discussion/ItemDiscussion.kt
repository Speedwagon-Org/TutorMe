package com.speedwagon.tutorme.Discussion

import android.os.Parcelable
import com.speedwagon.tutorme.R
import kotlinx.parcelize.Parcelize


@Parcelize
data class ItemDiscussion (
    var username : String? = "Dummy",
    var Text : String? = "there is no centent in here.",
    val ProfilePicture : Int? = R.drawable.ic_baseline_person_24,): Parcelable