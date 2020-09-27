package com.irfanirawansukirman.featuredashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PodsStatus(val status: String, val name: String) : Parcelable