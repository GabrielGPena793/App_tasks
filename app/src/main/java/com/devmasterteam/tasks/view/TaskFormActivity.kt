package com.devmasterteam.tasks.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.databinding.ActivityTaskFormBinding
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.viewmodel.TaskFormViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TaskFormActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: TaskFormViewModel
    private lateinit var binding: ActivityTaskFormBinding
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    private var listPriority: List<PriorityModel> = mutableListOf()
    private var taskId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Variáveis da classe
        viewModel = ViewModelProvider(this).get(TaskFormViewModel::class.java)
        binding = ActivityTaskFormBinding.inflate(layoutInflater)

        // Eventos
        binding.buttonSave.setOnClickListener(this)
        binding.buttonDate.setOnClickListener(this)

        observer()

        viewModel.loadPriority()
        loadDataFromActivity()

        // Layout
        setContentView(binding.root)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_date) {
            handleDate()
        } else if (v.id == R.id.button_save) {
            handleSave()
        }
    }

    override fun onDateSet(v: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val dueDate = dateFormat.format(calendar.time)
        binding.buttonDate.text = dueDate
    }

    private fun loadDataFromActivity() {
        val bundle = intent.extras
        if (bundle != null) {
            taskId = bundle.getInt(TaskConstants.BUNDLE.TASKID)
            viewModel.loadTask(taskId)
        }
    }

    private fun observer() {
        viewModel.priorityList.observe(this) { priorities ->
            listPriority = priorities
            val array = priorities.map { it.description }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array)
            binding.spinnerPriority.adapter = adapter
        }

        viewModel.response.observe(this) {
            if (it.status()) {

                if (taskId == 0) {
                    toast("Tarefa criada com sucesso")
                } else {
                    toast("Tarefa atualizada com sucesso")
                }

                finish()
            } else {
                toast(it.message())
            }
        }

        viewModel.taskLoad.observe(this) { task ->
            binding.editDescription.setText(task.description)
            binding.spinnerPriority.setSelection(getIndex(task.priorityId))
            binding.checkComplete.isChecked = task.complete

            val date = SimpleDateFormat("yyyy-MM-dd").parse(task.dueDate)
            binding.buttonDate.text = date?.let { dateFormat.format(it) }
        }
    }

    private fun getIndex(priorityId: Int): Int {
        var indexPriority = 0
        for ((index, priority) in listPriority.withIndex()) {
            if (priority.id == priorityId) {
                indexPriority = index
                break
            }
        }

        return indexPriority
    }

    private fun toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleSave() {
        val task = TaskModel().apply {
            this.id = taskId
            this.description = binding.editDescription.text.toString()
            this.dueDate = binding.buttonDate.text.toString()
            this.complete = binding.checkComplete.isChecked

            val index = binding.spinnerPriority.selectedItemPosition
            this.priorityId = listPriority[index].id
        }

        viewModel.save(task)
    }

    private fun handleDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, this, year, month, dayOfMonth).show()
    }
}