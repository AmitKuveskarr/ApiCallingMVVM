package com.example.apicallingmvvm.data.repositories

import com.example.apicallingmvvm.data.local.model.UserResponse
import com.example.apicallingmvvm.data.network.ApiService
import com.example.apicallingmvvm.data.network.Resource
import com.example.apicallingmvvm.data.network.SafeApiRequest
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SafeApiRequest() {



//    suspend fun getItem(cin: String ): Resource<UserResponse> {
//        return apiRequest { apiService.ItemApi(cin,"Abc") }
//    }
}
