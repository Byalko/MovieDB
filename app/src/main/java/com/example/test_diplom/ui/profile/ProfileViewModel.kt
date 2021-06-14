package com.example.test_diplom.ui.profile

import androidx.lifecycle.*
import com.example.test_diplom.data.model.db.DetailFilmDB
import com.example.test_diplom.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    val movies : LiveData<List<DetailFilmDB>> = databaseRepository.movieFlow.asLiveData()

    fun saveMovie(movie:DetailFilmDB){
        viewModelScope.launch {
            withContext(IO){
                databaseRepository.insert(movie)
            }
        }
    }

    fun deleteMovie(movie:DetailFilmDB){
        viewModelScope.launch {
            withContext(IO){
                databaseRepository.delete(movie)
            }
        }
    }
}