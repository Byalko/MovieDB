package com.example.test_diplom.data.model.searchFragment

import com.example.test_diplom.data.model.db.DetailFilmDB

data class SearchItem(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun SearchItem.toDetailFilmDB() = DetailFilmDB(
    id = id,
    poster_path = poster_path ?: "null",
    backdrop_path = backdrop_path ?: "null",
    overview = overview,
    release_date = release_date,
    title = title,
    vote_average = vote_average,
    vote_count = vote_count
)
