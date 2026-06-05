package com.example.apicallingmvvm.di.repository

import com.example.apicallingmvvm.di.network.ApiService
import com.example.apicallingmvvm.di.network.Resource
import com.example.apicallingmvvm.model.UserRequest
import com.example.apicallingmvvm.model.UserResponse
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getItem(cin: String, clientSecret: String): Resource<UserResponse> {
        return try {
            val request = UserRequest(clientSecret, cin)
            val response = apiService.getSchemeTypeGift(request)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Resource.Success(body)
            } else {
                Resource.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error")
        }
    }
}
