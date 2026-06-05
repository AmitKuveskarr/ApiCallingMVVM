package com.example.apicallingmvvm.di.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apicallingmvvm.di.network.Resource
import com.example.apicallingmvvm.model.UserResponse
import com.example.apicallingmvvm.di.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    private val _users = MutableLiveData<Resource<UserResponse>>()
    val users: LiveData<Resource<UserResponse>> get() = _users

    fun fetchUser(cin: String, clientSecret: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _users.postValue(Resource.Loading())
            try {
                val result = repository.getItem(cin, clientSecret)
                _users.postValue(result)
            } catch (e: Exception) {
                _users.postValue(Resource.Error(e.message ?: "Error"))
            }
        }
    }
}
