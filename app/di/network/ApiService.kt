package com.example.apicallingmvvm.di.network

import com.example.apicallingmvvm.model.UserRequest
import com.example.apicallingmvvm.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST()
    suspend fun getSchemeTypeGift(
        @Body request: UserRequest
    ): Response<UserResponse>

//    @PUT("UpdateScheme/{id}")
//    suspend fun updateScheme(
//        @Path("id") id: Int,
//        @Body request: UserRequest
//    ): Response<UserResponse>

}
