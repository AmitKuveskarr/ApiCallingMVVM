package com.example.apicallingmvvm.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.apicallingmvvm.data.database.AppDatabase
import com.example.apicallingmvvm.data.local.dao.UserDao
import com.example.apicallingmvvm.data.network.ApiService
import com.example.apicallingmvvm.data.network.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiService(networkConnectionInterceptor: NetworkConnectionInterceptor) : ApiService {
        return ApiService.invoke(networkConnectionInterceptor)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "api_calling_db"
        ).build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.getUserDao()
    }
}
