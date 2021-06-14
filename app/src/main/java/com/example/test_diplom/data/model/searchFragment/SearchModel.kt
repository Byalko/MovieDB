package com.example.test_diplom.data.model.searchFragment

data class SearchModel(
    val page: Int,
    val results: List<SearchItem>,
    val total_pages: Int,
    val total_results: Int
)