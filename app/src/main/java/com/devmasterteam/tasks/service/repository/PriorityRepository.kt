package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.repository.remote.PriorityService
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriorityRepository(val context: Context) {

    private val api = RetrofitClient.createService(PriorityService::class.java)

    fun list() {
        val call = api.list()
        call.enqueue(object : Callback<List<PriorityModel>> {
            override fun onResponse(
                call: Call<List<PriorityModel>>,
                response: Response<List<PriorityModel>>
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<List<PriorityModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}