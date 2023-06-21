package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.devmasterteam.tasks.service.repository.remote.TaskService

class TaskRepository(context: Context): BaseRepository(context) {

    private val remote = RetrofitClient.createService(TaskService::class.java)

    fun create(task: TaskModel, listener: APIListener<Boolean>) {

        val call = remote.create(task.priorityId, task.description, task.dueDate, task.complete)
        handleRequest(call, listener)
    }

    fun list(listener: APIListener<List<TaskModel>>) {
        val call = remote.list()
        handleRequest(call, listener)
    }

    fun listNext(listener: APIListener<List<TaskModel>>) {
        val call = remote.listNext()
        handleRequest(call, listener)
    }

    fun listOverDue(listener: APIListener<List<TaskModel>>) {
        val call = remote.listOverDue()
        handleRequest(call, listener)
    }

    fun delete(id: Int, listener: APIListener<Boolean>) {

        val call = remote.delete(id)
        handleRequest(call, listener)
    }
}