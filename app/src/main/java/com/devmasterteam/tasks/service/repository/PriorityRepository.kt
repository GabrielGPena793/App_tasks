package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.repository.local.TaskDatabase
import com.devmasterteam.tasks.service.repository.remote.PriorityService
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriorityRepository(val context: Context): BaseRepository() {

    private val api = RetrofitClient.createService(PriorityService::class.java)
    private val localDataBase = TaskDatabase.getDatabase(context).priorityDAO()

    fun list(listener: APIListener<List<PriorityModel>>) {
        val call = api.list()
        handleRequest(context, call, listener)
    }

    fun list (): List<PriorityModel> {
        return localDataBase.list()
    }

    fun save(priorities: List<PriorityModel>) {
        localDataBase.clear()
        localDataBase.save(priorities)
    }
}