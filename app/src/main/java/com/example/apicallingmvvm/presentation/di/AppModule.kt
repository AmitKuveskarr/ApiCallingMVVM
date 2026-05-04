package com.example.apicallingmvvm.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.apicallingmvvm.data.local.AppDatabase
import com.example.apicallingmvvm.data.local.dao.UserDao
import com.example.apicallingmvvm.data.network.ApiService
import com.example.apicallingmvvm.data.network.NetworkConnectionInterceptor
import com.example.apicallingmvvm.data.prefs.PrefProvider
import com.example.apicallingmvvm.data.repositories.RepositoryImpl
import com.example.apicallingmvvm.domain.Repository
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

    @Singleton
    @Provides
    fun provideRepository(
        apiService: ApiService,
        prefProvider: PrefProvider,
        userDao: UserDao
    ): Repository {
        return RepositoryImpl(apiService, prefProvider, userDao)
    }
}
