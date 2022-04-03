package com.speedwagon.tutorme.Discussion

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ItemDiscussion (
    var username : String? = null,
    val content : String? = null,): Parcelable