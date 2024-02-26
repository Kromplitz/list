package com.example.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyViewModel @Inject constructor(val repo:Repository) :ViewModel() {
    private val _uiState = MutableLiveData<UIState>(UIState.Empty)
    val uiState: LiveData<UIState> = _uiState


    fun getData() {
        _uiState.value = UIState.Processing
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val response = repo.getHeroByName()
                if ((response != null) && response.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        _uiState.postValue(
                           UIState.Result (response))}
                } else {
                    _uiState.postValue(UIState.Error("Error"))
                }
            }
        }
    }
    sealed class UIState {
        object Empty : UIState()
        object Processing : UIState()
        class Result(val heroes: List<Hero>) : UIState()
        class Error(val description: String) : UIState()
    }

}


