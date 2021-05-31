package com.example.test_diplom.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_diplom.data.model.homeFragment.ItemHome
import com.example.test_diplom.data.model.homeFragment.detail.DetailFilm
import com.example.test_diplom.data.model.homeFragment.popular.ListFilm
import com.example.test_diplom.repository.HomeRepository
import com.example.test_diplom.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
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
    private val _category = MutableLiveData<List<ItemHome>>()
    val category: LiveData<List<ItemHome>> = _category

    fun getFilm(id: Int?) {
        if (id == null) {
            _film.value = FilmEvent.Failure("Incorrect id_film")
            return
        }

        viewModelScope.launch {
            _film.value = FilmEvent.Loading
            try {
                withContext(IO) {
                    val call = async { homeRepository.getDetailFilm(id) }
                    when (val response = call.await()) {
                        is Resource.Success -> _film.value = FilmEvent.Success(response.data!!)
                        else -> {
                            _film.value = FilmEvent.Failure(response.message ?: "Loading Error")
                            call.cancel()
                        }
                    }
                }
                multiplyCategory(id)
            } catch (e: Exception) {
                _film.value = FilmEvent.Failure(e.message.toString())
            }
        }
    }

    private suspend fun multiplyCategory(id: Int) {
        val res: MutableList<ItemHome> = mutableListOf()
        val destination = "detail"
        withContext(IO) {
            val call1 = async { homeRepository.getSimilarFilm(id) }
            checkAsyncLiveData(call1, res, "Similar Movie", destination)
            val call2 = async { homeRepository.getRecommendationFilm(id) }
            checkAsyncLiveData(call2, res, "Recommendation Movie", destination)
            _category.postValue(res)
        }
    }

    private suspend fun checkAsyncLiveData(
        call: Deferred<Resource<ListFilm>>,
        res: MutableList<ItemHome>,
        title: String,
        destination: String
    ) {
        when (val response = call.await()) {
            is Resource.Success -> {
                if (response.data!!.results.isNotEmpty()) {
                    res.add(ItemHome(title, response.data, destination))
                }
            }
            is Resource.Error -> {
                _film.value = FilmEvent.Failure(response.message ?: "Loading Error")
                call.cancel()
            }
        }
    }
}

sealed class FilmEvent {
    class Success(val result: DetailFilm) : FilmEvent()
    class Failure(val errorText: String) : FilmEvent()
    object Loading : FilmEvent()
    object Empty : FilmEvent()
}