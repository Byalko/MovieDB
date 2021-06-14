package com.example.test_diplom.data

import com.example.test_diplom.data.model.homeFragment.detail.DetailFilm
import com.example.test_diplom.data.model.homeFragment.popular.ListFilm
import com.example.test_diplom.data.model.searchFragment.SearchModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiTMDB {

    @GET("movie/popular")
    suspend fun getListPopular(): Response<ListFilm>

    @GET("movie/top_rated")
    suspend fun getListTopRated(): Response<ListFilm>

    @GET("movie/upcoming")
    suspend fun getListUpcoming(): Response<ListFilm>

    @GET("movie/{movie_id}")
    suspend fun getDetailFilm(@Path("movie_id") id: Int): Response<DetailFilm>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarFilm(@Path("movie_id") id: Int): Response<ListFilm>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendationFilm(@Path("movie_id") id: Int): Response<ListFilm>

    @GET("search/movie")
    suspend fun getSearchFilm(@Query("query") query: String): Response<SearchModel>

}