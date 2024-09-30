package com.example.notifeyetest

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class TaskEditDialog(context: Context, private val task: Task, private val onTaskUpdated: (Task) -> Unit) : Dialog(context) {

    private lateinit var etTaskTitle: EditText
    private lateinit var etTaskDescription: EditText
    private lateinit var etTaskStartTime: EditText
    private lateinit var etTaskEndTime: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_edit_dialog)

        etTaskTitle = findViewById(R.id.etTaskTitle)
        etTaskDescription = findViewById(R.id.etTaskDescription)
        etTaskStartTime = findViewById(R.id.etTaskStartTime)
        etTaskEndTime = findViewById(R.id.etTaskEndTime)
        val btnSave = findViewById<Button>(R.id.btnSave)

        // Populate fields with current task data
        etTaskTitle.setText(task.title)
        etTaskDescription.setText(task.description)
        etTaskStartTime.setText(task.startTime)
        etTaskEndTime.setText(task.endTime)

        btnSave.setOnClickListener {
            val updatedTask = task.copy(
                title = etTaskTitle.text.toString(),
                description = etTaskDescription.text.toString(),
                startTime = etTaskStartTime.text.toString(),
                endTime = etTaskEndTime.text.toString()
            )
            onTaskUpdated(updatedTask)
            dismiss()
        }
    }
}
