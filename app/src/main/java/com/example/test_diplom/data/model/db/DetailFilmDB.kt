package com.example.test_diplom.data.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.test_diplom.data.model.homeFragment.detail.DetailFilm

@Entity(tableName = "movie")
data class DetailFilmDB(
    @PrimaryKey
    val id:Int,
    val poster_path: String?,
    val backdrop_path: String,
    val overview: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)

fun DetailFilm.toDetailFilmDB() = DetailFilmDB(
    id = id,
    poster_path = poster_path.toString(),
    backdrop_path = backdrop_path,
    overview = overview,
    release_date = release_date,
    title = title,
    vote_average = vote_average,
    vote_count = vote_count
)
