package com.example.test_diplom.repository

import com.example.test_diplom.data.ApiTMDB
import com.example.test_diplom.data.model.searchFragment.SearchModel
import com.example.test_diplom.util.Resource
import com.example.test_diplom.util.checkHttpResponse
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: ApiTMDB
) {

    suspend fun getSearchFilm(query: String): Resource<SearchModel> =
        checkHttpResponse(api.getSearchFilm(query))

}