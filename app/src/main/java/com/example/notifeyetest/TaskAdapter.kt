package com.example.notifeyetest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private var tasks: List<Task>, private val onItemClick: (Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // ViewHolder for each task item
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.tvTaskDescription)
        val dateTextView: TextView = itemView.findViewById(R.id.tvTaskDate)

        init {
            itemView.setOnClickListener {
                onItemClick(tasks[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.titleTextView.text = task.taskName
        holder.descriptionTextView.text = task.description
        holder.dateTextView.text = task.date
    }

    override fun getItemCount(): Int = tasks.size

    // Method to update the task list
    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks // Update the list with new tasks
        notifyDataSetChanged()
    }
}
