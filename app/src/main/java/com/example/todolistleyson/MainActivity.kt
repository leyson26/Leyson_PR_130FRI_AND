package com.example.todolistleyson

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import todolistleyson.Task
import todolistleyson.TaskAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var tasks: MutableList<Task>
    private lateinit var taskInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tasks = mutableListOf()
        taskAdapter = TaskAdapter(tasks, ::editTask, ::deleteTask)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskInput = findViewById(R.id.editTextTask)
        val addButton: FloatingActionButton = findViewById(R.id.fabAdd)

        addButton.setOnClickListener {
            val taskName = taskInput.text.toString()
            if (taskName.isNotEmpty()) {
                val task = Task(taskName)
                tasks.add(task)
                taskAdapter.notifyItemInserted(tasks.size - 1)
                taskInput.text.clear()
            }
        }
    }

    private fun editTask(position: Int) {
        val task = tasks[position]

        val editDialog = AlertDialog.Builder(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_task, null)
        val editText = dialogView.findViewById<EditText>(R.id.editTextTaskName)
        editText.setText(task.name)

        editDialog.setView(dialogView)
        editDialog.setPositiveButton("Update") { _, _ ->
            val updatedTaskName = editText.text.toString()
            if (updatedTaskName.isNotEmpty()) {
                tasks[position].name = updatedTaskName
                taskAdapter.notifyItemChanged(position)
            }
        }
        editDialog.setNegativeButton("Cancel", null)
        editDialog.show()
    }

    private fun deleteTask(position: Int) {
        tasks.removeAt(position)
        taskAdapter.notifyItemRemoved(position)
    }
}
