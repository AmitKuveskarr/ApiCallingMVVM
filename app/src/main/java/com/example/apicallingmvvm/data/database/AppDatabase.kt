package com.example.apicallingmvvm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apicallingmvvm.data.local.dao.UserDao
import com.example.apicallingmvvm.data.local.entities.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}