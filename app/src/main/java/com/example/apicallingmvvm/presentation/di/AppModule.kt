package com.example.apicallingmvvm.presentation.di

import android.content.Context
import com.example.apicallingmvvm.data.network.ApiService
import com.example.apicallingmvvm.data.network.NetworkConnectionInterceptor
import com.example.apicallingmvvm.data.repositories.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.invoke

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiService(networkConnectionInterceptor: NetworkConnectionInterceptor) : ApiService {
        return ApiService.invoke(networkConnectionInterceptor)
    }

}