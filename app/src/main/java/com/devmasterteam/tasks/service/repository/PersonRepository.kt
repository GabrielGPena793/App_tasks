package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.repository.remote.PersonService
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository(context: Context): BaseRepository(context) {

    private val api = RetrofitClient.createService(PersonService::class.java)

    fun login(email: String, password: String, listener: APIListener<PersonModel>) {

        val call = api.login(email, password)
        handleRequest(call, listener)
    }
}