package com.example.test_diplom.repository

import com.example.test_diplom.data.ApiTMDB
import com.example.test_diplom.data.model.homeFragment.detail.DetailFilm
import com.example.test_diplom.data.model.homeFragment.popular.ListFilm
import com.example.test_diplom.util.Resource
import com.example.test_diplom.util.checkHttpResponse
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: ApiTMDB
) {

    suspend fun getRecommendationFilm(id:Int) : Resource<ListFilm> = checkHttpResponse(api.getRecommendationFilm(id))

    suspend fun getSimilarFilm(id:Int) : Resource<ListFilm> = checkHttpResponse(api.getSimilarFilm(id))

    suspend fun getDetailFilm(id:Int): Resource<DetailFilm> = checkHttpResponse(api.getDetailFilm(id))

    suspend fun getListUpcoming(page: Int?): Resource<ListFilm> = checkHttpResponse(api.getListUpcoming(page))

    suspend fun getListPopular(page: Int?): Resource<ListFilm> = checkHttpResponse(api.getListPopular(page))

    suspend fun getListTopRated(page: Int?): Resource<ListFilm> = checkHttpResponse(api.getListTopRated(page))
}