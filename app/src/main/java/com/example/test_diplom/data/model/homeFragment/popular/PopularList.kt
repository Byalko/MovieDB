package com.example.test_diplom.data.model.homeFragment.popular


import com.squareup.moshi.Json

data class PopularList(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<ItemHome>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)