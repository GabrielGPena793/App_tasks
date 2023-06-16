package com.devmasterteam.tasks.service.repository.remote

import com.devmasterteam.tasks.service.constants.TaskConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {
        private lateinit var INSTANCE: Retrofit
        private const val BASE_URL = "http://devmasterteam.com/CursoAndroidAPI/"
        private var token = ""
        private var personKey = ""

        private fun getRetrofitInstance(): Retrofit {
            val http = OkHttpClient.Builder()

            // ====== intercepitando uma request
            http.addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(TaskConstants.HEADER.PERSON_KEY, token)
                    .addHeader(TaskConstants.HEADER.TOKEN_KEY, personKey)
                    .build()

                chain.proceed(request)
            }

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

        fun addHeaders(tokenValue: String, personKeyValue : String) {
            token = tokenValue
            personKey = personKeyValue
        }
    }
}