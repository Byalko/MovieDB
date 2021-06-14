package com.example.test_diplom.db

import androidx.room.*
import com.example.test_diplom.data.model.db.DetailFilmDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAll(): Flow<List<DetailFilmDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: DetailFilmDB)

    @Delete
    suspend fun delete(movie: DetailFilmDB)
}