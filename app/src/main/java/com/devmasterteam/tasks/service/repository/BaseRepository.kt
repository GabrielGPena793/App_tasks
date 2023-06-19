package com.devmasterteam.tasks.service.repository

import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.APIListener
import com.google.gson.Gson
import retrofit2.Response

open class BaseRepository {

    private fun convertJson(json: String) : String {
        return Gson().fromJson(json, String::class.java)
    }

    fun <T> handleResponse(response: Response<T>, listener: APIListener<T>) {
        if (response.code() == TaskConstants.HTTP.SUCCESS) {
            response.body()?.let { listener.onSucess(it) }
        } else {
            val errorMessage = convertJson(response.errorBody()!!.string())
            listener.onFailure(errorMessage)
        }
    }
}