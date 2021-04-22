package com.example.test_diplom.repository

import com.example.test_diplom.data.ApiTMDB
import com.example.test_diplom.data.model.homeFragment.popular.ListFilm
import com.example.test_diplom.util.Resource
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: ApiTMDB
) {

    suspend fun getListUpcoming(): Resource<ListFilm> = checkHttpResponse(api.getListUpcoming())

    suspend fun getListPopular(): Resource<ListFilm> = checkHttpResponse(api.getListPopular())

    suspend fun getListTopRated(): Resource<ListFilm> = checkHttpResponse(api.getListTopRated())

    private fun checkHttpResponse(response: Response<ListFilm>): Resource<ListFilm> {
        return try {
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}