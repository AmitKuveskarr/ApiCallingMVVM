package com.example.apicallingmvvm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.apicallingmvvm.data.repositories.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : ViewModel() {
//
//    private val _data = MutableLiveData<Resource<List<>>>()
//    val Data: LiveData<Resource<List<>>> get() = _data
//
//    fun fetchdata(
//        cin: String,
//
//    ) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _itemWisePending.postValue(Resource.Loading())
//            try {
//                val response = repository.getItem(cin)
//
//                if (response is Resource.Success) {
//
////                    val flatList = response.data?.flatMap { item ->
////                        item.data.flatMap { data -> data. }
////                    } ?: emptyList()
//
////                    _itemWisePending.postValue(Resource.Success(flatList))
//                } else {
//                    _itemWisePending.postValue(Resource.Error(response.message ?: "Unknown Error"))
//                }
//            } catch (e: Exception) {
//                _itemWisePending.postValue(Resource.Error("Error: ${e.message}"))
//            }
//        }
//    }
}
