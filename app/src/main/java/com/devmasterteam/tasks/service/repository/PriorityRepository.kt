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

class PriorityRepository(context: Context): BaseRepository(context) {

    private val api = RetrofitClient.createService(PriorityService::class.java)
    private val localDataBase = TaskDatabase.getDatabase(context).priorityDAO()

    // Cache para evitar acessos desnecessários ao banco
    companion object {
        private val cacheList = mutableMapOf<Int, String>()

        fun getCacheDescription(id: Int): String {
            return cacheList[id] ?: ""
        }

        fun setCache(id: Int, description: String) {
            cacheList[id] = description
        }
    }

    fun getDescription(id: Int): String {
        val cache = getCacheDescription(id)
        return if (cache == "") {
            val description = localDataBase.getDescription(id)
            setCache(id, description)
            description
        } else {
            cache
        }
    }

    fun list(listener: APIListener<List<PriorityModel>>) {
        val call = api.list()
        handleRequest(call, listener)
    }

    fun list (): List<PriorityModel> {
        return localDataBase.list()
    }

    fun save(priorities: List<PriorityModel>) {
        localDataBase.clear()
        localDataBase.save(priorities)
    }
}