package com.example.test_diplom.data.model.genre


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreX(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
) : Parcelable