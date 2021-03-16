package com.example.test_diplom.data

import com.example.test_diplom.data.model.Films
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface ApiFilms{
    @Headers(
        "accept: application/json",
        "X-API-KEY: d4c4fe2c-8ef7-4737-9ccb-a425df7f07d3"
    )
    @GET("/api/v2.1/films/{id}")
    suspend fun getFilm(@Path("id")id : Int): Response<Films>

    /*@Headers(
            "accept: application/json",
            "X-API-KEY: d4c4fe2c-8ef7-4737-9ccb-a425df7f07d3"
    )
    @GET("/api/v2.1/films/{id}/videos")
    suspend fun getVideos(@Path("id")id : Int): Response<Videos>
*/

}