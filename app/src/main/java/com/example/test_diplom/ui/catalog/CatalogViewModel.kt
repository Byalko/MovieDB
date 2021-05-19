package com.example.test_diplom.ui.catalog

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
) : ViewModel() {

    private val _film = MutableStateFlow<FilmEvent>(FilmEvent.Empty)
    val film: StateFlow<FilmEvent> = _film

    init {
        //getFilm("301")
    }

    /*fun getFilm(id_string: String?) {

        if (id_string.isNullOrEmpty()) {
            _film.value = FilmEvent.Failure("Valid id")
            return
        }
        val id = id_string.toInt()

        viewModelScope.launch(IO) {
            _film.value = FilmEvent.Loading

            when (val response = repository.getFilm(id)) {
                is Resource.Error -> _film.value = FilmEvent.Failure(response.message!!)
                is Resource.Success -> {
                    val film = response.data?.data?.nameEn
                    if (film == null) {
                        _film.value = FilmEvent.Failure("No name")
                    } else {
                        _film.value = FilmEvent.Success(film)
                    }

                }
            }
        }
    }*/
}

sealed class FilmEvent {
    class Success(val resultText: String) : FilmEvent()
    class Failure(val errorText: String) : FilmEvent()
    object Loading : FilmEvent()
    object Empty : FilmEvent()
}