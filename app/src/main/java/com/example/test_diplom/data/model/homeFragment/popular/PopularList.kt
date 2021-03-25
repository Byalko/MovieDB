package com.example.test_diplom.data.model.homeFragment.popular


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class PopularList(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<Result>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)