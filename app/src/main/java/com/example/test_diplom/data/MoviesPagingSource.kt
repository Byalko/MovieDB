package com.example.test_diplom.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.test_diplom.data.model.homeFragment.popular.ItemFilm
import com.example.test_diplom.data.model.homeFragment.popular.ListFilm
import retrofit2.HttpException
import retrofit2.Response

class MoviesPagingSource(
    private val api: ApiTMDB,
    private val destination: String
) : PagingSource<Int, ItemFilm>() {
    override fun getRefreshKey(state: PagingState<Int, ItemFilm>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemFilm> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        val response = check(destination, page)
        if (response.isSuccessful) {
            val movie = checkNotNull(response.body()).results
            val nextKey = if (movie.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            return LoadResult.Page(movie, prevKey, nextKey)
        } else {
            return LoadResult.Error(HttpException(response))
        }

    }

    private suspend fun check(destination: String, page: Int): Response<ListFilm> {
        return when (destination) {
            "Popular" -> api.getListPopular(page)
            "TopRated" -> api.getListTopRated(page)
            "Upcoming" -> api.getListUpcoming(page)
            else -> api.getListPopular(page)
        }
    }
}