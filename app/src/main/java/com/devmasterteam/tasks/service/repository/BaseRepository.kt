package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.APIListener
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseRepository {

    private fun convertJson(json: String) : String {
        return Gson().fromJson(json, String::class.java)
    }

    fun <T> handleRequest(context: Context, call: Call<T>, listener: APIListener<T>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                if (response.code() == TaskConstants.HTTP.SUCCESS) {
                    response.body()?.let { listener.onSucess(it) }
                } else {
                    val errorMessage = convertJson(response.errorBody()!!.string())
                    listener.onFailure(errorMessage)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }
        })
    }
}