package com.example.notifeyetest

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class activity_context_aware_tasks : AppCompatActivity() {

    private lateinit var etTaskType: EditText
    private lateinit var btnGetSuggestions: Button
    private lateinit var rvSuggestedTasks: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var suggestedTasks: List<Task> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_context_aware_tasks)

        // Initialize views
        etTaskType = findViewById(R.id.etTaskType)
        btnGetSuggestions = findViewById(R.id.btnGetSuggestions)
        rvSuggestedTasks = findViewById(R.id.rvSuggestedTasks)

        // Set up RecyclerView
        rvSuggestedTasks.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(suggestedTasks) { task -> onTaskClick(task) }
        rvSuggestedTasks.adapter = taskAdapter

        // Set listener for the button
        btnGetSuggestions.setOnClickListener {
            val taskType = etTaskType.text.toString()
            if (taskType.isNotEmpty()) {
                getTaskSuggestions(taskType)
            } else {
                Toast.makeText(this, "Please enter a task type", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTaskSuggestions(taskType: String) {
        suggestedTasks = when (taskType.lowercase()) {
            "work" -> listOf(
                Task("Finish report", "09:00", "10:00", "Complete the quarterly report", "01-10-2024", "Work", "Monday", "Finish report"),
                Task("Team meeting", "11:00", "12:00", "Discuss project updates", "02-10-2024", "Work", "Tuesday", "Team meeting"),
                Task("Code Review", "14:00", "15:00", "Review the code for the new feature", "03-10-2024", "Work", "Wednesday", "Code Review"),
                Task("Client Call", "16:00", "17:00", "Discuss project requirements", "04-10-2024", "Work", "Thursday", "Client Call"),
                Task("Project Planning", "10:00", "12:00", "Plan the next steps for the project", "05-10-2024", "Work", "Friday", "Project Planning")
            )
            "personal" -> listOf(
                Task("Grocery shopping", "10:00", "11:00", "Buy groceries for the week", "01-10-2024", "Personal", "Monday", "Grocery shopping"),
                Task("Gym workout", "06:00", "07:00", "Morning workout session", "02-10-2024", "Personal", "Tuesday", "Gym workout"),
                Task("Doctor Appointment", "09:00", "10:00", "Visit the doctor for a checkup", "03-10-2024", "Personal", "Wednesday", "Doctor Appointment"),
                Task("Coffee with Friend", "14:00", "15:00", "Catch up with a friend over coffee", "04-10-2024", "Personal", "Thursday", "Coffee with Friend"),
                Task("Movie Night", "19:00", "22:00", "Watch a movie at home", "05-10-2024", "Personal", "Friday", "Movie Night")
            )
            else -> emptyList()
        }

        taskAdapter.updateTasks(suggestedTasks)
    }

    private fun onTaskClick(task: Task) {
        // Open a dialog or a new activity to edit the task details
        val dialog = TaskEditDialog(this, task) { updatedTask ->
            // Handle adding the task to the calendar
            addTaskToCalendar(updatedTask)
        }
        dialog.show()
    }

    private fun addTaskToCalendar(task: Task) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, task.title)
            putExtra(CalendarContract.Events.DESCRIPTION, task.description)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, task.startTime)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, task.endTime)
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No Calendar app found", Toast.LENGTH_SHORT).show()
        }
    }
}
