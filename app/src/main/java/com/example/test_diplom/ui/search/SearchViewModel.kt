package com.example.test_diplom.ui.search

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_diplom.data.model.homeFragment.popular.ListFilm
import com.example.test_diplom.data.model.searchFragment.SearchModel
import com.example.test_diplom.repository.SearchRepository
import com.example.test_diplom.util.Resource
import com.example.test_diplom.util.hasInternetConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @ApplicationContext private val app: Context,
    private val searchRepository: SearchRepository
) : ViewModel() {
    val searchNews: MutableLiveData<Resource<SearchModel>> = MutableLiveData()

    fun getSearchFilm(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        if (hasInternetConnection(app)) {
            withContext(Dispatchers.IO) {
                val response = searchRepository.getSearchFilm(searchQuery)
                when (response) {
                    is Resource.Success -> searchNews.postValue(Resource.Success(response.data!!))
                    is Resource.Error -> searchNews.postValue(Resource.Error(response.message.toString()))
                    else -> searchNews.postValue(Resource.Error("Error"))
                }
            }
        } else {
            searchNews.postValue(Resource.Error("No internet connection"))
        }
    }
}