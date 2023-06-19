package com.devmasterteam.tasks.service.repository.remote

import com.devmasterteam.tasks.service.model.TaskModel
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskService {

    @GET("Task")
    fun list(): Call<List<TaskModel>>

    @GET("Task/Next7Days")
    fun listNext(): Call<List<TaskModel>>

    @GET("Task/OverDue")
    fun listOverDue(): Call<List<TaskModel>>

    @GET("Task/{id}")
    fun getTaskById(@Path(value = "id", encoded = true) id: String): Call<List<TaskModel>>

    @POST("Task")
    @FormUrlEncoded
    fun create(
        @Field("PriorityId") priorityId: Int,
        @Field("Description") description: String,
        @Field("DueDate") dueDate: String,
        @Field("Complete") complete: Boolean
    ): Call<Boolean>

    @PUT("Task")
    @FormUrlEncoded
    fun update(
        @Field("id") id: Int,
        @Field("PriorityId") priorityId: Int,
        @Field("Description") description: String,
        @Field("DueDate") dueDate: String,
        @Field("Complete") complete: Boolean
    ): Call<Boolean>

    @PUT("Task/Complete")
    @FormUrlEncoded
    fun completeTask(
        @Field("id") id: Int,
    ): Call<Boolean>

    @PUT("Task/Undo")
    @FormUrlEncoded
    fun undoTask(
        @Field("id") id: Int,
    ): Call<Boolean>

    @DELETE
    @FormUrlEncoded
    fun delete(
        @Field("id") id: Int
    ): Call<Boolean>
}