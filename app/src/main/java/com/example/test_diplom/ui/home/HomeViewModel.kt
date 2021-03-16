package com.example.test_diplom.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_diplom.data.model.genre.Genre_list
import com.example.test_diplom.repository.CatalogRepository
import com.example.test_diplom.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository
) : ViewModel() {

    private val _genr = MutableStateFlow<GenreEvent>(GenreEvent.Empty)
    val genr: StateFlow<GenreEvent> = _genr

    init {
        //getGenres()
    }

    fun getGenres() {

        viewModelScope.launch(Dispatchers.IO) {
            _genr.value = GenreEvent.Loading

            when (val response = catalogRepository.getListGenre()) {
                is Resource.Error -> _genr.value = GenreEvent.Failure(response.message!!)
                is Resource.Success -> {
                    val list = response.data
                    if (list == null) {
                        _genr.value = GenreEvent.Failure("No data")
                    } else {
                        _genr.value = GenreEvent.Success(list)
                    }
                }
            }
        }

    }

    /*viewModelScope.launch(Dispatchers.IO) {
        _film.value = Event.Loading

        when (val response = repository.getFilm(id)) {
            is Resource.Error -> _film.value = Event.Failure(response.message!!)
            is Resource.Success -> {
                val film = response.data?.data?.nameEn
                if (film == null) {
                    _film.value = Event.Failure("No name")
                } else {
                    _film.value = Event.Success(film)
                }

            }
        }
    }*/

}

sealed class GenreEvent {
    class Success(val resultText: Genre_list) : GenreEvent()
    class Failure(val errorText: String) : GenreEvent()
    object Loading : GenreEvent()
    object Empty : GenreEvent()
}