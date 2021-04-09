package com.example.test_diplom.data.model.genre


import com.squareup.moshi.Json

data class Genre_list(
    @Json(name = "genres")
    val genres: List<Result>
)