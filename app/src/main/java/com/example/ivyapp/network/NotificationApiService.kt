package com.example.ivyapp.network

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
// import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://my-json-server.typicode.com/Dogeuzman/mockjson/"

private val notifMoshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val notifRetrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(notifMoshi))
    .baseUrl(BASE_URL)
    .build()

interface NotificationApiService {
    @GET("notifications")
//    suspend fun getNotifications(): List<Notification>
    suspend fun getNotifications(): List<Notification>
}

object NotificationApi {
    val notificationRetrofitService: NotificationApiService by lazy {
        notifRetrofit.create(NotificationApiService::class.java)
    }
}