package com.example.apicallingmvvm.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apicallingmvvm.data.model.DashboardItemWisePendingResponse
import com.example.apicallingmvvm.data.network.Resource
import com.example.apicallingmvvm.data.repositories.RepositoryImpl
import com.example.apicallingmvvm.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardItemWisePendingViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : ViewModel() {

    private val _ItemWisePending = MutableLiveData<Resource<DashboardItemWisePendingResponse>>()
    val ItemWisePending: LiveData<Resource<DashboardItemWisePendingResponse>> get() = _ItemWisePending

    fun fetchItemWisePending(
        cin: String,
        division: String,
        type: String,
        index: String,
        count: String,
        asonDate: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _ItemWisePending.postValue(Resource.Loading())
            try {
                val apiResponse = repository.getItemWisePending(
                    cin,
                    division,
                    type,
                    index,
                    count,
                    asonDate
                )
                _ItemWisePending.postValue(apiResponse)
            } catch (e: Exception) {
                _ItemWisePending.postValue(Resource.Error("Error fetching data: ${e.message}"))
            }
        }
    }
}
