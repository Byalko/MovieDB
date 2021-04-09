package com.example.test_diplom.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_diplom.data.model.homeFragment.ItemHome
import com.example.test_diplom.repository.HomeRepository
import com.example.test_diplom.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _all = MutableStateFlow<AllEvent>(AllEvent.Empty)
    val all: StateFlow<AllEvent> = _all

    init {
        getAll()
    }

    fun getAll() {
        viewModelScope.launch {
            _all.value = AllEvent.Loading
            try {
                val res: MutableList<ItemHome> = mutableListOf()
                coroutineScope {
                    val call1 = async(Dispatchers.IO) { homeRepository.getListPopular() }
                    when (val response = call1.await()) {
                        is Resource.Success -> {
                            res.add(ItemHome("Popular", response.data!!))
                            res.add(ItemHome("Popular", response.data))
                        }
                        else -> {
                            _all.value = AllEvent.Failure(response.message.toString())
                        }
                    }

                    val call2 = async(Dispatchers.IO) { homeRepository.getListPopular() }
                    when (val response = call2.await()) {
                        is Resource.Success -> {
                            res.add(ItemHome("Popular", response.data!!))
                            res.add(ItemHome("Popular", response.data))
                        }
                        else -> {
                            _all.value = AllEvent.Failure(response.message.toString())
                        }
                    }
                    _all.value = AllEvent.Success(res)

                }
            } catch (e: Exception) {
                _all.value = AllEvent.Failure(e.message.toString())
            }
        }
    }

}

sealed class AllEvent {
    class Success(val resultText: List<ItemHome>) : AllEvent()
    class Failure(val errorText: String) : AllEvent()
    object Loading : AllEvent()
    object Empty : AllEvent()
}