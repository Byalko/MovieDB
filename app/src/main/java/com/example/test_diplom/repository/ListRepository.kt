package com.example.test_diplom.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.test_diplom.data.ApiTMDB
import com.example.test_diplom.data.MoviesPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRepository @Inject constructor(
    private val api:ApiTMDB
) {

    fun getList(destination:String) = Pager(
        config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {MoviesPagingSource(api,destination)}
    ).liveData
}