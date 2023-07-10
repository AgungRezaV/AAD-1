package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailTaskViewModel

    private lateinit var edTitle: TextInputEditText
    private lateinit var edDescription: TextInputEditText
    private lateinit var edDueDate: TextInputEditText
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        initTask()
    }

    private fun initTask(){
        edTitle = findViewById(R.id.detail_ed_title)
        edDescription = findViewById(R.id.detail_ed_description)
        edDueDate = findViewById(R.id.detail_ed_due_date)
        btnDelete = findViewById(R.id.btn_delete_task)

        val idTask = intent.getIntExtra(TASK_ID, 0)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailTaskViewModel::class.java]
        viewModel.setTaskId(idTask)
        val taskObserver = Observer<Task> {
            edTitle.setText(it.title)
            edDescription.setText(it.description)
            edDueDate.setText(DateConverter.convertMillisToString(it.dueDateMillis))
        }

        viewModel.task.observe(this, taskObserver)

        btnDelete.setOnClickListener {
            viewModel.task.removeObserver(taskObserver)
            viewModel.deleteTask()
            finish()
        }
    }
}