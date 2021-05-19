package com.example.test_diplom.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_diplom.data.model.homeFragment.detail.DetailFilm
import com.example.test_diplom.repository.HomeRepository
import com.example.test_diplom.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _film = MutableStateFlow<FilmEvent>(FilmEvent.Empty)
    val film: StateFlow<FilmEvent> = _film

    fun getFilm(id: Int?) {
        if (id == null) {
            _film.value = FilmEvent.Failure("Incorrect id_film")
            return
        }

        viewModelScope.launch {
            _film.value = FilmEvent.Loading

            withContext(IO) {
                when (val response = homeRepository.getDetailFilm(id)) {
                    is Resource.Success -> _film.value = FilmEvent.Success(response.data!!)
                    else -> _film.value = FilmEvent.Failure(response.message.toString())
                }
            }
        }
    }
}

sealed class FilmEvent {
    class Success(val result:DetailFilm):FilmEvent()
    class Failure(val errorText: String) : FilmEvent()
    object Loading : FilmEvent()
    object Empty : FilmEvent()
}