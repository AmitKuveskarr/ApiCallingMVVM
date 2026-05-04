package com.example.apicallingmvvm.domain

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
}
