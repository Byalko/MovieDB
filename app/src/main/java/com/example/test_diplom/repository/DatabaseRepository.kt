package com.example.test_diplom.repository

import com.example.test_diplom.data.model.db.DetailFilmDB
import com.example.test_diplom.db.MovieDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val db: MovieDatabase
) {

    suspend fun insert(movie: DetailFilmDB) = db.movieDao().insert(movie)

    suspend fun delete(movie: DetailFilmDB) = db.movieDao().delete(movie)

    val movieFlow: Flow<List<DetailFilmDB>>
        get() = db.movieDao().getAll()

}