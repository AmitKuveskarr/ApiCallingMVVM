package com.example.apicallingmvvm.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apicallingmvvm.data.model.DashboardItemWisePendingResponse
import com.example.apicallingmvvm.data.network.Resource
import com.example.apicallingmvvm.data.repositories.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardItemWisePendingViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : ViewModel() {

    private val _itemWisePending = MutableLiveData<Resource<List<DashboardItemWisePendingResponse.DashboardItemWisePendingResponseItem.Pendingdata>>>()
    val itemWisePending: LiveData<Resource<List<DashboardItemWisePendingResponse.DashboardItemWisePendingResponseItem.Pendingdata>>> get() = _itemWisePending

    fun fetchItemWisePending(
        cin: String,
        division: String,
        type: String,
        index: String,
        count: String,
        asonDate: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _itemWisePending.postValue(Resource.Loading())
            try {
                val response = repository.getItemWisePending(cin, division, type, index, count, asonDate)
                
                if (response is Resource.Success) {
                    // Mapping the complex nested response to a simple flat list for the UI
                    val flatList = response.data?.flatMap { item ->
                        item.data.flatMap { data -> data.pendingdata }
                    } ?: emptyList()
                    
                    _itemWisePending.postValue(Resource.Success(flatList))
                } else {
                    _itemWisePending.postValue(Resource.Error(response.message ?: "Unknown Error"))
                }
            } catch (e: Exception) {
                _itemWisePending.postValue(Resource.Error("Error: ${e.message}"))
            }
        }
    }
}
