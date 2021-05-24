package com.example.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FooRequest(val id: Int) : Parcelable

@Parcelize
data class FooResponse(val result: String) : Parcelable

@Parcelize
class RemoteError(val errorCode: Int, val message: String?) : Parcelable