package com.example.notifeyetest

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class activity_add_task : AppCompatActivity () {

    private lateinit var taskTitle: EditText
    private lateinit var taskDescription: EditText
    private lateinit var locationEditText: EditText
    private lateinit var startTimeEditText: EditText
    private lateinit var endTimeEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var addTaskButton: Button

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private var startTimeMillis: Long = 0
    private var endTimeMillis: Long = 0
    private var selectedDateMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_task)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://notifeyetest-default-rtdb.europe-west1.firebasedatabase.app/").reference

        // Initialize views
        taskTitle = findViewById(R.id.taskTitle)
        taskDescription = findViewById(R.id.taskDescription)
        locationEditText = findViewById(R.id.locationEditText)
        startTimeEditText = findViewById(R.id.startTimeEditText)
        endTimeEditText = findViewById(R.id.endTimeEditText)
        dateEditText = findViewById(R.id.dateEditText)
        categoryEditText = findViewById(R.id.categoryEditText)
        addTaskButton = findViewById(R.id.addTaskButton)

        // Set listeners for date/time pickers
        startTimeEditText.setOnClickListener { pickDateTime { startTimeMillis = it; startTimeEditText.setText(formatTime(it)) } }
        endTimeEditText.setOnClickListener { pickDateTime { endTimeMillis = it; endTimeEditText.setText(formatTime(it)) } }
        dateEditText.setOnClickListener { pickDate { selectedDateMillis = it; dateEditText.setText(formatDate(it)) } }

        // Add task to Firebase and Calendar
        addTaskButton.setOnClickListener {
            addTaskToFirebase()
        }
    }

    // Function to display Date and Time picker
    private fun pickDateTime(onDateTimeSelected: (Long) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            TimePickerDialog(this, { _, hourOfDay, minute ->
                calendar.set(year, month, dayOfMonth, hourOfDay, minute)
                onDateTimeSelected(calendar.timeInMillis)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    // Function to display Date picker
    private fun pickDate(onDateSelected: (Long) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            onDateSelected(calendar.timeInMillis)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    // Format the selected time into a readable string
    private fun formatTime(timeInMillis: Long): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(timeInMillis))
    }

    // Format the selected date into a readable string
    private fun formatDate(dateInMillis: Long): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return sdf.format(Date(dateInMillis))
    }

    // Function to add task to Firebase Realtime Database
    private fun addTaskToFirebase() {
        val title = taskTitle.text.toString()
        val description = taskDescription.text.toString()
        val location = locationEditText.text.toString()
        val category = categoryEditText.text.toString()

        if (title.isNotEmpty() && description.isNotEmpty() && startTimeMillis > 0 && endTimeMillis > 0 && selectedDateMillis > 0) {
            val userId = auth.currentUser?.uid ?: return
            val taskId = database.child("users").child(userId).child("tasks").push().key ?: return

            val task = Task(
                taskName = title,
                startTime = formatTime(startTimeMillis),
                endTime = formatTime(endTimeMillis),
                description = description,
                date = formatDate(selectedDateMillis),
                category = category,
                dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date(selectedDateMillis)),
                title = title,
                startMillis = startTimeMillis,
                endMillis = endTimeMillis
            )

            database.child("users").child(userId).child("tasks").child(taskId).setValue(task)
                .addOnSuccessListener {
                    Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show()
                    addTaskToCalendar(task)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to add task to the user's calendar
    private fun addTaskToCalendar(task: Task) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, task.title)
            putExtra(CalendarContract.Events.DESCRIPTION, task.description)
            putExtra(CalendarContract.Events.EVENT_LOCATION, task.category)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTimeMillis)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTimeMillis)
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No Calendar app found", Toast.LENGTH_SHORT).show()
        }
    }
}
