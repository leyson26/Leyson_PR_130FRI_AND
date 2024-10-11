package com.example.bottomnavigationleyson

import com.example.bottomnavigationleyson.Task
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskListFragment : Fragment() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskList: MutableList<Task>
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextTaskName: EditText
    private lateinit var addButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerView)
        editTextTaskName = view.findViewById(R.id.editTextTask)
        addButton = view.findViewById(R.id.fabAdd) // Ensure you have a FloatingActionButton in your fragment layout

        // Initialize task list and adapter
        taskList = mutableListOf()
        taskAdapter = TaskAdapter(taskList, ::onEditTask, ::onDeleteTask)

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = taskAdapter

        // Handle add button click
        addButton.setOnClickListener {
            addTask()
        }

        return view
    }

    private fun addTask() {
        val taskName = editTextTaskName.text.toString() // Get the task name from EditText
        if (taskName.isNotEmpty()) { // Check if the task name is not empty
            val newTask = Task(id = System.currentTimeMillis(), title = taskName) // Create a new Task
            taskList.add(newTask) // Add the new Task to the task list
            taskAdapter.notifyItemInserted(taskList.size - 1) // Notify the adapter
            editTextTaskName.text.clear() // Clear the input field
        }
    }

    private fun onEditTask(position: Int) {
        val task = taskList[position]

        val editDialog = AlertDialog.Builder(requireContext())
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_task, null)
        val editText = dialogView.findViewById<EditText>(R.id.editTextTaskName)
        editText.setText(task.title)

        editDialog.setView(dialogView)
        editDialog.setPositiveButton("Update") { _, _ ->
            val updatedTaskName = editText.text.toString()
            if (updatedTaskName.isNotEmpty()) {
                taskList[position].title = updatedTaskName
                taskAdapter.notifyItemChanged(position)
            }
        }
        editDialog.setNegativeButton("Cancel", null)
        editDialog.show()
    }

    private fun onDeleteTask(position: Int) {
        taskList.removeAt(position)
        taskAdapter.notifyItemRemoved(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear references to avoid memory leaks
    }
}
