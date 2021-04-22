package com.example.test_diplom.di

import com.example.test_diplom.data.ApiTMDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
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
        level = HttpLoggingInterceptor.Level.HEADERS
        level = HttpLoggingInterceptor.Level.BODY
    }


    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(provideLoggingInterceptor())
        addInterceptor { chain -> return@addInterceptor addApiKeyToRequests(chain) }
    }.build()

    private fun addApiKeyToRequests(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url
        val newUrl = originalHttpUrl.newBuilder().addQueryParameter(
            "api_key", "0693b17c06c6556d4d5186dfad80a064"
        ).build()
        request.url(newUrl)
        return chain.proceed(request.build())
    }

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