package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.devmasterteam.tasks.service.repository.remote.TaskService

class TaskRepository(context: Context) : BaseRepository(context) {

    private val remote = RetrofitClient.createService(TaskService::class.java)

    fun create(task: TaskModel, listener: APIListener<Boolean>) {

        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.create(task.priorityId, task.description, task.dueDate, task.complete)
        handleRequest(call, listener)
    }

    fun list(listener: APIListener<List<TaskModel>>) {

        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.list()
        handleRequest(call, listener)
    }

    fun listNext(listener: APIListener<List<TaskModel>>) {

        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.listNext()
        handleRequest(call, listener)
    }

    fun listOverDue(listener: APIListener<List<TaskModel>>) {

        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.listOverDue()
        handleRequest(call, listener)
    }

    fun delete(id: Int, listener: APIListener<Boolean>) {

        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.delete(id)
        handleRequest(call, listener)
    }

    fun completeTask(id: Int, listener: APIListener<Boolean>) {

        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.completeTask(id)
        handleRequest(call, listener)
    }

    fun undoTask(id: Int, listener: APIListener<Boolean>) {

        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.undoTask(id)
        handleRequest(call, listener)
    }

    fun updateTask(task: TaskModel, listener: APIListener<Boolean>) {

        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.update(task.id, task.priorityId, task.description, task.dueDate, task.complete)
        handleRequest(call, listener)
    }

    fun getTaskById(id: Int, listener: APIListener<TaskModel> ) {

        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.getTaskById(id)
        handleRequest(call, listener)
    }
}