package com.devmasterteam.tasks.service.repository.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {
        private lateinit var INSTANCE: Retrofit
        private const val BASE_URL = "http://devmasterteam.com/CursoAndroidAPI/"

        private fun getRetrofitInstance(): Retrofit {
            val http = OkHttpClient.Builder()

            if (!::INSTANCE.isInitialized) {
                INSTANCE = synchronized(RetrofitClient::class) {
                    Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(http.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }

            return INSTANCE
        }

        fun <T> createService(service: Class<T>): T {
            return getRetrofitInstance().create(service)
        }
    }
}