package com.example.test_diplom.data

import com.example.test_diplom.data.model.Genre
import com.example.test_diplom.data.model.genre.Genre_list
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiTMDB {

    @GET("genre/movie/list?api_key=0693b17c06c6556d4d5186dfad80a064&language=ru-RU")
    suspend fun getListGenres() : Response<Genre_list>
}