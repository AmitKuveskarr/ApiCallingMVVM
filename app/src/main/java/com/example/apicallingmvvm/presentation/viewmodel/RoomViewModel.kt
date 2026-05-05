package com.example.apicallingmvvm.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apicallingmvvm.data.local.entities.User
import com.example.apicallingmvvm.data.repositories.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : ViewModel() {

    val allUsers: LiveData<List<User>> = repository.getAllLocalUsers()

    fun insert(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }

    fun update(user: User) = viewModelScope.launch {
        repository.updateUser(user)
    }

    fun delete(user: User) = viewModelScope.launch {
        repository.deleteUser(user)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAllLocalUsers()
    }

    fun deleteById(userId: Int) = viewModelScope.launch {
        repository.deleteUserById(userId)
    }
}
