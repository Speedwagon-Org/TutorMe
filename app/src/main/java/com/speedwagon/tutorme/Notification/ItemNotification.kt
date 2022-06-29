package com.speedwagon.tutorme.Notification

import android.os.Parcelable
import com.speedwagon.tutorme.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemNotification (
        var id :String="null",
        var Username: String = "Guest",
        ): Parcelable