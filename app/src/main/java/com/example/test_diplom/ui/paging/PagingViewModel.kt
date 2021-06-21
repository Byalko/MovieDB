package com.example.test_diplom.ui.paging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.test_diplom.data.MoviesPagingSource
import com.example.test_diplom.data.model.homeFragment.popular.ItemFilm
import com.example.test_diplom.repository.HomeRepository
import com.example.test_diplom.repository.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class PagingViewModel @Inject constructor(
    private val listRepository: ListRepository
) : ViewModel() {

    private val destination = MutableLiveData(DEFAULT_DESTINATION)

    val movies = destination.switchMap { destination->
        listRepository.getList(destination).cachedIn(viewModelScope)
    }

    fun toDestination(dest:String){
        destination.value = dest
    }

    companion object{
        private const val DEFAULT_DESTINATION="Popular"
    }
}