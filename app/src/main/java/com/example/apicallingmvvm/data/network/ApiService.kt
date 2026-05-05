package com.example.apicallingmvvm.data.network

import com.example.apicallingmvvm.BuildConfig
import com.example.apicallingmvvm.presentation.utils.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiService {

//    @FormUrlEncoded
//    @POST("")
//    suspend fun ItemApi(
//        @Field("CIN") cin: String,
//    ): Response<>

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
