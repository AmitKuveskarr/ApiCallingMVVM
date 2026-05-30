package com.example.apicallingmvvm.data.network

import com.example.apicallingmvvm.BuildConfig
import com.example.apicallingmvvm.data.local.model.Todo
import com.example.apicallingmvvm.presentation.utils.Constant
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiService {

    @POST("todos")
    suspend fun createTodo(@Body todo: Todo): Response<Todo>

    @PUT("todos/{id}")
    suspend fun updateTodo(@Path("id") id: Int, @Body todo: Todo): Response<Todo>

    @PATCH("todos/{id}")
    suspend fun patchTodo(@Path("id") id: Int, @Body todo: Map<String, @JvmSuppressWildcards Any>): Response<Todo>

    @DELETE("todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Int): Response<ResponseBody>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): ApiService {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .addInterceptor(networkConnectionInterceptor)
                .apply {
                    if (BuildConfig.DEBUG) {
                        addInterceptor(logging)
                    }
                }
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constant.TEST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
