package com.example.apicallingmvvm.data.repositories

import androidx.lifecycle.LiveData
import com.example.apicallingmvvm.data.local.dao.UserDao
import com.example.apicallingmvvm.data.local.entities.User
import com.example.apicallingmvvm.data.network.ApiService
import com.example.apicallingmvvm.data.network.Resource
import com.example.apicallingmvvm.data.network.SafeApiRequest
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : SafeApiRequest() {

//    suspend fun getItem(
//        cin: String,
//    ): Resource<> {
//        return apiRequest {
//            apiService.ItemApi(
//
//            )
//        }
//    }

    // Room DB Operations
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    fun getAllLocalUsers(): LiveData<List<User>> {
        return userDao.getAllUsers()
    }

    suspend fun deleteAllLocalUsers() {
        userDao.deleteAllUsers()
    }

    suspend fun deleteUserById(userId: Int) {
        userDao.deleteUserById(userId)
    }
}
