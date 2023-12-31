package com.devmasterteam.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.service.model.ValidationModel
import com.devmasterteam.tasks.service.repository.PriorityRepository
import com.devmasterteam.tasks.service.repository.TaskRepository

class TaskListViewModel(application: Application) : AndroidViewModel(application) {

    private val taskRepository = TaskRepository(application.applicationContext)
    private val priorityRepository = PriorityRepository(application.applicationContext)
    private var _taskFilter = 0

    private val _tasks = MutableLiveData<List<TaskModel>>()
    val tasks: LiveData<List<TaskModel>> = _tasks

    private val _validation = MutableLiveData<ValidationModel>()
    val validation : LiveData<ValidationModel> = _validation

    private val utilComumListener = object : APIListener<Boolean> {
        override fun onSucess(result: Boolean) {
            list(_taskFilter)
        }

        override fun onFailure(message: String) {
            _validation.value = ValidationModel(message)
        }
    }

    fun list(taskFilter: Int) {
        _taskFilter = taskFilter

        val listener = object : APIListener<List<TaskModel>> {
            override fun onSucess(result: List<TaskModel>) {
                result.forEach {
                    it.priorityDescription = priorityRepository.getDescription(it.priorityId)
                }

                _tasks.value = result
            }

            override fun onFailure(message: String) {
                _validation.value = ValidationModel(message)
            }
        }

        when (taskFilter) {
            TaskConstants.FILTER.ALL -> taskRepository.list(listener)
            TaskConstants.FILTER.NEXT -> taskRepository.listNext(listener)
            else -> taskRepository.listOverDue(listener)
        }

    }

    fun delete(id: Int) {
        taskRepository.delete(id, utilComumListener)
    }

    fun completeTask(id: Int) {
        taskRepository.completeTask(id, utilComumListener)
    }

    fun undoTask(id: Int) {
        taskRepository.undoTask(id, utilComumListener)
    }

}