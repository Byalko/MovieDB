package com.example.test_diplom.repository

import com.example.test_diplom.data.ApiTMDB
import com.example.test_diplom.data.model.homeFragment.popular.PopularList
import com.example.test_diplom.util.Resource
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: ApiTMDB
) {

    suspend fun getListUpcoming(): Resource<PopularList> = checkHttpResponse(api.getListUpcoming())

    suspend fun getListPopular(): Resource<PopularList> = checkHttpResponse(api.getListPopular())

    suspend fun getListTopRated(): Resource<PopularList> = checkHttpResponse(api.getListTopRated())

    private fun checkHttpResponse(response: Response<PopularList>): Resource<PopularList> {
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