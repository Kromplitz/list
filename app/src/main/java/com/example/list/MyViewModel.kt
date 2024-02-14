package com.example.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel:ViewModel() {
    private val _uiState = MutableLiveData<UIState>(UIState.Empty)
    val uiState: LiveData<UIState> = _uiState
    private val repo = MyApplication.getApp().repo
    fun getData() {
        _uiState.value = UIState.Processing
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repo.getHeroByName("name", "images")
                if (response?.name!= null && response?.images!=null) {
                    withContext(Dispatchers.Main) {
                        _uiState.postValue(
                            UIState.Result("${response.name} ${response.images}"))
                    }
                } else
                    _uiState.postValue(UIState.Error("Error"))
            }
        }
    }
    sealed class UIState {
        object Empty : UIState()
        object Processing : UIState()
        class Result(val title: String) : UIState()
        class Error(val description: String) : UIState()
    }

}