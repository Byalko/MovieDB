package com.example.test_diplom.repository

import com.example.test_diplom.data.ApiTMDB
import com.example.test_diplom.data.model.genre.Genre_list
import com.example.test_diplom.util.Resource
import javax.inject.Inject

class CatalogRepository @Inject constructor(
    private val api: ApiTMDB
) {

    suspend fun getListGenre(): Resource<Genre_list> {
        return try {
            val response = api.getListGenres()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error("Error code : ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something go wrong")
        }
    }
}