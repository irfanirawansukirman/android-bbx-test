package com.irfanirawansukirman.featuredashboard.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@SuppressLint("ParcelCreator")
@Parcelize
@Serializable
data class Pods(
    @SerialName("code")
    val code: Int?,
    @SerialName("data")
    val `data`: List<PodsData>?,
    @SerialName("message")
    val message: String?,
    @SerialName("params")
    val params: List<HashMap<String, String>>?,
    @SerialName("status")
    val status: Int?
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
@Serializable
data class PodsData(
    @SerialName("description")
    val description: String?,
    @SerialName("mqttTopic")
    val mqttTopic: String?,
    @SerialName("qrCode")
    val qrCode: String?,
    @SerialName("roomId")
    val roomId: Int?,
    @SerialName("roomName")
    val roomName: String?,
    @SerialName("roomNotes")
    val roomNotes: List<RoomNote>?,
    @SerialName("roomStatus")
    val roomStatus: Int?,
    @SerialName("roomStatusCode")
    val roomStatusCode: String?,
    @SerialName("roomStatusLabel")
    val roomStatusLabel: String?,
    @SerialName("roomType")
    val roomType: String?,
    @SerialName("roomTypeLabel")
    val roomTypeLabel: String?
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
@Serializable
data class RoomNote(
    @SerialName("noteCode")
    val noteCode: String?,
    @SerialName("noteId")
    val noteId: Int?,
    @SerialName("noteName")
    val noteName: String?,
    var selected: Boolean = false
) : Parcelable