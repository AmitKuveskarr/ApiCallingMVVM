package com.example.apicallingmvvm.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apicallingmvvm.data.local.model.UserResponse
import com.example.apicallingmvvm.data.network.Resource
import com.example.apicallingmvvm.data.repositories.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : ViewModel() {
    

    private val _users = MutableLiveData<Resource<UserResponse>>()
    val users: LiveData<Resource<UserResponse>> get() = _users

    fun fetchUser(cin: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _users.postValue(Resource.Loading<UserResponse>())
            try {
                val apiResponse: Resource<UserResponse> =
                    repository.getItem(cin) // Fetch data from repository
                _users.postValue(apiResponse) // Post the response
            } catch (e: Exception) {
                _users.postValue(Resource.Error<UserResponse>("Error fetching data: ${e.message}"))
            }
        }
    }


}
