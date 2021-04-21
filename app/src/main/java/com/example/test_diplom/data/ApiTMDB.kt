package com.example.test_diplom.data

import com.example.test_diplom.data.model.genre.Genre_list
import com.example.test_diplom.data.model.homeFragment.popular.PopularList
import retrofit2.Response
import retrofit2.http.GET

interface ApiTMDB {

    @GET("genre/movie/list?language=ru-RU")
    suspend fun getListGenres() : Response<Genre_list>

    @GET("movie/popular")
    suspend fun getListPopular() : Response<PopularList>

    @GET("movie/top_rated")
    suspend fun getListTopRated() : Response<PopularList>

    @GET("movie/upcoming")
    suspend fun getListUpcoming() : Response<PopularList>
    
}