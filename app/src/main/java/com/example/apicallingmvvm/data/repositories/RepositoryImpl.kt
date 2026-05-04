package com.example.apicallingmvvm.data.repositories

import androidx.lifecycle.LiveData
import com.example.apicallingmvvm.data.local.dao.UserDao
import com.example.apicallingmvvm.data.local.entities.User
import com.example.apicallingmvvm.data.model.DashboardItemWisePendingResponse
import com.example.apicallingmvvm.data.network.ApiService
import com.example.apicallingmvvm.data.network.Resource
import com.example.apicallingmvvm.data.network.SafeApiRequest
import com.example.apicallingmvvm.data.prefs.PrefProvider
import com.example.apicallingmvvm.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val prefProvider: PrefProvider,
    private val userDao: UserDao
) : SafeApiRequest(), Repository {

    override suspend fun getItemWisePending(
        cin: String,
        division: String,
        type: String,
        index: String,
        count: String,
        asonDate: String
    ): Resource<DashboardItemWisePendingResponse> {
        return apiRequest {
            apiService.ItemWisePendingApi(
                cin, "201020", division, "", asonDate, index, count, type
            )
        }
    }



//RoomDb
    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    override fun getAllLocalUsers(): LiveData<List<User>> {
        return userDao.getAllUsers()
    }

    override suspend fun deleteAllLocalUsers() {
        userDao.deleteAllUsers()
    }
}
