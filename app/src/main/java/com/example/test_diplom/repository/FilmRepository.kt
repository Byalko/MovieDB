package com.example.test_diplom.repository

import com.example.test_diplom.data.ApiFilms
import com.example.test_diplom.data.model.Films
import com.example.test_diplom.util.Resource
import javax.inject.Inject

class FilmRepository @Inject constructor(
    private val api: ApiFilms
) {

    suspend fun getFilm(id:Int): Resource<Films> {
        return try {
            val response = api.getFilm(id)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error("Error code: ${response.code()}")
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }

    /*suspend fun getVideos(id:Int):Resource<Videos1>{
        return try{
            val response = api.getVideos(id)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error("Error code: ${response.code()}")
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }*/
}