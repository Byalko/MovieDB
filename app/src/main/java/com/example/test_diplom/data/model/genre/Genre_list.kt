package com.example.test_diplom.data.model.genre


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Genre_list(
    @Json(name = "genres")
    val genres: List<GenreX>
)