package com.example.test_diplom.data

import com.example.test_diplom.data.model.genre.Genre_list
import com.example.test_diplom.data.model.homeFragment.detail.DetailFilm
import com.example.test_diplom.data.model.homeFragment.popular.ListFilm
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiTMDB {

    @GET("genre/movie/list?language=ru-RU")
    suspend fun getListGenres() : Response<Genre_list>

    @GET("movie/popular")
    suspend fun getListPopular() : Response<ListFilm>

    @GET("movie/top_rated")
    suspend fun getListTopRated() : Response<ListFilm>

    @GET("movie/upcoming")
    suspend fun getListUpcoming() : Response<ListFilm>

    @GET("movie/{movie_id}")
    suspend fun getDetailFilm(@Path("movie_id")id : Int) : Response<DetailFilm>
}