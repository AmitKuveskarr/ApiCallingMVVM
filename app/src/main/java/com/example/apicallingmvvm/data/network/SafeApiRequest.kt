package com.example.apicallingmvvm.data.network

import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend() -> Response<T>) : Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.Success(body)
            }
            return Resource.Error("Error Code: ${response.code()} \n ${response.message()}")
        } catch (e: Exception) {
            return Resource.Error(e.message ?: e.toString())
        }
    }
}