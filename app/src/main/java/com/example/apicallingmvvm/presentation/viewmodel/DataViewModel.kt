package com.example.apicallingmvvm.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apicallingmvvm.data.local.model.Todo
import com.example.apicallingmvvm.data.network.Resource
import com.example.apicallingmvvm.data.repositories.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : ViewModel() {
    

    private val _users = MutableLiveData<Resource<List<Todo>>>()
    val users: LiveData<Resource<List<Todo>>> get() = _users

    private val _todoResult = MutableLiveData<Resource<Todo>>()
    val todoResult: LiveData<Resource<Todo>> get() = _todoResult

    private val _deleteResult = MutableLiveData<Resource<Unit>>()
    val deleteResult: LiveData<Resource<Unit>> get() = _deleteResult

    fun fetchUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _users.postValue(Resource.Loading())
            try {
                val apiResponse: Resource<List<Todo>> =
                    repository.getItem() // Fetch data from repository
                _users.postValue(apiResponse) // Post the response
            } catch (e: Exception) {
                _users.postValue(Resource.Error("Error fetching data: ${e.message}"))
            }
        }
    }

    fun createTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            _todoResult.postValue(Resource.Loading())
            try {
                val response = repository.createTodo(todo)
                _todoResult.postValue(response)
            } catch (e: Exception) {
                _todoResult.postValue(Resource.Error(e.message ?: "Error creating todo"))
            }
        }
    }

    fun updateTodo(id: Int, todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            _todoResult.postValue(Resource.Loading())
            try {
                val response = repository.updateTodo(id, todo)
                _todoResult.postValue(response)
            } catch (e: Exception) {
                _todoResult.postValue(Resource.Error(e.message ?: "Error updating todo"))
            }
        }
    }

    fun patchTodo(id: Int, todo: Map<String, Any>) {
        viewModelScope.launch(Dispatchers.IO) {
            _todoResult.postValue(Resource.Loading())
            try {
                val response = repository.patchTodo(id, todo)
                _todoResult.postValue(response)
            } catch (e: Exception) {
                _todoResult.postValue(Resource.Error(e.message ?: "Error patching todo"))
            }
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _deleteResult.postValue(Resource.Loading())
            try {
                val response = repository.deleteTodo(id)
                _deleteResult.postValue(response)
            } catch (e: Exception) {
                _deleteResult.postValue(Resource.Error(e.message ?: "Error deleting todo"))
            }
        }
    }


}
