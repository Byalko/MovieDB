package com.example.test_diplom.di

import android.content.Context
import androidx.room.Room
import com.example.test_diplom.db.MovieDatabase
import com.example.test_diplom.db.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "movie_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

}