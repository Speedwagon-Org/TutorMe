package com.speedwagon.tutorme.Profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataProfile(
    var id: String ="",
    var deskripsi: String = "Belum Teriisi !!!"
): Parcelable
