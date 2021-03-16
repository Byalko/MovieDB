package com.example.test_diplom.data.model.genre


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class GenreX(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
)