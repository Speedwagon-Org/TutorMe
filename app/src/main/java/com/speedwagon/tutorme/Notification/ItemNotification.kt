package com.speedwagon.tutorme.Notification

import android.os.Parcelable
import com.speedwagon.tutorme.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemNotification (
        val imageprofile :Int = R.drawable.ic_baseline_person_24,
        val Username: String = "Guest",
        ): Parcelable