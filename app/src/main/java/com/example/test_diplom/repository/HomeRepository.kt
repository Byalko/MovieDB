package com.example.test_diplom.repository

import com.example.test_diplom.data.ApiTMDB
import com.example.test_diplom.data.model.homeFragment.popular.PopularList
import com.example.test_diplom.util.Resource
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: ApiTMDB
) {

    suspend fun getListPopular(): Resource<PopularList> {
        return try {
            val response = api.getListPopular()
            val result = response.body()
            if (response.isSuccessful && result!=null){
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e : Exception) {
            Resource.Error(e.message.toString())
        }
    }
}