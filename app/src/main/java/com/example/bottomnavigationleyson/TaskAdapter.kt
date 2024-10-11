package com.example.bottomnavigationleyson

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onEditTask: (Int) -> Unit,
    private val onDeleteTask: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val task = tasks[position]
        holder.bind(task)

        // Handle double tap (for edit/delete)
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            private var lastClickTime: Long = 0

            override fun onClick(view: View?) {
                val clickTime = System.currentTimeMillis()
                if (clickTime - lastClickTime < 300) { // Double-tap detected
                    showEditDeleteDialog(holder.itemView.context, position)
                }
                lastClickTime = clickTime
            }
        })
    }

    override fun getItemCount(): Int = tasks.size

    private fun showEditDeleteDialog(context: Context, position: Int) {
        val options = arrayOf("Edit", "Delete")
        AlertDialog.Builder(context)
            .setTitle("Select Action")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> onEditTask(position)  // Edit selected
                    1 -> onDeleteTask(position) // Delete selected
                }
            }
            .show()
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBoxTask: CheckBox = itemView.findViewById(R.id.checkBox)
        private val textViewTaskTitle: TextView = itemView.findViewById(R.id.textViewTaskName)

        fun bind(task: Task) {
            textViewTaskTitle.text = task.title
            checkBoxTask.isChecked = task.isCompleted
            checkBoxTask.setOnCheckedChangeListener { _, isChecked ->
                task.isCompleted = isChecked
            }
        }
    }
}

