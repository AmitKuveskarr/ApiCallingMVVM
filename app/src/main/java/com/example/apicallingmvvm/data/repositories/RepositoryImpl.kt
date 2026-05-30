package com.example.apicallingmvvm.data.repositories

import com.example.apicallingmvvm.data.local.model.Todo
import com.example.apicallingmvvm.data.network.ApiService
import com.example.apicallingmvvm.data.network.Resource
import com.example.apicallingmvvm.data.network.SafeApiRequest
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SafeApiRequest() {



    suspend fun getItem(): Resource<List<Todo>> {
        return apiRequest { apiService.ItemApi() }
    }

    suspend fun createTodo(todo: Todo): Resource<Todo> {
        return apiRequest { apiService.createTodo(todo) }
    }

    suspend fun updateTodo(id: Int, todo: Todo): Resource<Todo> {
        return apiRequest { apiService.updateTodo(id, todo) }
    }

    suspend fun patchTodo(id: Int, todo: Map<String, Any>): Resource<Todo> {
        return apiRequest { apiService.patchTodo(id, todo) }
    }

    suspend fun deleteTodo(id: Int): Resource<Unit> {
        val response = apiService.deleteTodo(id)
        return if (response.isSuccessful) {
            Resource.Success(Unit)
        } else {
            Resource.Error("Error Code: ${response.code()} \n ${response.message()}")
        }
    }
}
