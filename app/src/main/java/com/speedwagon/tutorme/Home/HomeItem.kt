package com.speedwagon.tutorme.Home

import android.os.Parcelable
import com.speedwagon.tutorme.R
import kotlinx.parcelize.Parcelize
@Parcelize
data class HomeItem (
    val ImageProfile: Int = R.drawable.ic_baseline_person_24,
    val Username: String = "Guest",
    val DiscussionContent: String = "test"): Parcelable