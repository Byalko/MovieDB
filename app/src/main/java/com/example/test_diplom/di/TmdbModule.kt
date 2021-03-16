package com.example.test_diplom.di

import com.example.test_diplom.data.ApiTMDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URLL = "https://api.themoviedb.org/3/"

@Module
@InstallIn(SingletonComponent::class)
object TmdbModule {
    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.HEADERS
        this.level = HttpLoggingInterceptor.Level.BODY
    }


    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(provideLoggingInterceptor())
    }.build()

    @Singleton
    @Provides
    fun provideApiTmdb(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URLL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(provideHttpClient())
        .build()

    @Singleton
    @Provides
    fun clientApiFilms(): ApiTMDB = provideApiTmdb().create(ApiTMDB::class.java)
}