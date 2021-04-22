package com.example.test_diplom.data.model.homeFragment.popular


import com.squareup.moshi.Json

data class ListFilm(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<ItemFilm>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)