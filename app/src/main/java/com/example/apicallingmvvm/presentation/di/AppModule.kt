package com.example.apicallingmvvm.presentation.di

import com.example.apicallingmvvm.data.network.ApiService
import com.example.apicallingmvvm.data.network.NetworkConnectionInterceptor
import com.example.apicallingmvvm.data.prefs.PrefProvider
import com.example.apicallingmvvm.data.repositories.RepositoryImpl
import com.example.apicallingmvvm.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService, prefProvider: PrefProvider): Repository {
        return RepositoryImpl(apiService, prefProvider)
    }
}