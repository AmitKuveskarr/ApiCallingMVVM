package com.example.apicallingmvvm.domain

import androidx.lifecycle.LiveData
import com.example.apicallingmvvm.data.local.entities.User
import com.example.apicallingmvvm.data.model.DashboardItemWisePendingResponse
import com.example.apicallingmvvm.data.network.Resource

interface Repository {
    suspend fun getItemWisePending(
        cin: String,
        division: String,
        type: String,
        index: String,
        count: String,
        asonDate: String
    ): Resource<DashboardItemWisePendingResponse>

    // User Room operations
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    fun getAllLocalUsers(): LiveData<List<User>>
    suspend fun deleteAllLocalUsers()
}
