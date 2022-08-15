package com.example.zapp_project.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val id: String?,
    val name: String?,
    val username: String?,
    val email: String?
) : Parcelable