package com.devmasterteam.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.service.model.ValidationModel
import com.devmasterteam.tasks.service.repository.PriorityRepository
import com.devmasterteam.tasks.service.repository.TaskRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val priorityRepository = PriorityRepository(application.applicationContext)
    private val taskRepository = TaskRepository(application.applicationContext)

    private val _priorityList = MutableLiveData<List<PriorityModel>>()
    val priorityList: LiveData<List<PriorityModel>> = _priorityList

    private val _response = MutableLiveData<ValidationModel>()
    val response: LiveData<ValidationModel> = _response


    private val _taskLoad = MutableLiveData<TaskModel>()
    val taskLoad: LiveData<TaskModel> = _taskLoad

    fun loadPriority() {
        _priorityList.value = priorityRepository.list()
    }

    fun loadTask(id: Int) {
        val task = taskRepository.getTaskById(id, object : APIListener<TaskModel> {
            override fun onSucess(result: TaskModel) {
                _taskLoad.value = result
            }

            override fun onFailure(message: String) {
               _response.value = ValidationModel(message)
            }
        })
    }

    fun save(task: TaskModel) {
        val listener = object : APIListener<Boolean> {
            override fun onSucess(result: Boolean) {
                _response.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _response.value = ValidationModel(message)
            }
        }

        if (task.id == 0) {
            taskRepository.create(task, listener)
        } else {
            taskRepository.updateTask(task, listener)
        }

    }
}