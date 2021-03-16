package com.example.test_diplom.di

import com.example.test_diplom.data.ApiFilms
import com.example.test_diplom.di.TmdbModule_ProvideInterceptorFactory.provideInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiFilms(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(provideInterceptor())
        .build()

    @Singleton
    @Provides
    fun clientApiFilms(): ApiFilms = provideApiFilms().create(ApiFilms::class.java)
}