package com.example.test_diplom.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test_diplom.data.model.db.DetailFilmDB

@Database(entities = [DetailFilmDB::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}