package com.example.notifeyetest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class activity_search_task : AppCompatActivity() {

    private lateinit var etSearchTerm: EditText
    private lateinit var btnSearch: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var taskList: MutableList<Task> = mutableListOf()
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_task)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://notifeyetest-default-rtdb.europe-west1.firebasedatabase.app/").reference

        etSearchTerm = findViewById(R.id.etSearchTerm)
        btnSearch = findViewById(R.id.btnSearch)
        recyclerView = findViewById(R.id.rvSearchResults)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter and set it to the RecyclerView
        taskAdapter = TaskAdapter(taskList) { }
        recyclerView.adapter = taskAdapter

        // Set up search button click listener
        btnSearch.setOnClickListener {
            val searchTerm = etSearchTerm.text.toString()
            if (searchTerm.isNotEmpty()) {
                searchTasks(searchTerm)
            } else {
                Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchTasks(searchTerm: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.child("users").child(userId).child("tasks")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // Clear the current task list
                        taskList.clear()

                        // Loop through the tasks in the snapshot
                        for (taskSnapshot in dataSnapshot.children) {
                            val task = taskSnapshot.getValue(Task::class.java)
                            if (task != null) {
                                // Check if the task title or description contains the search term
                                if (task.taskName.contains(searchTerm, ignoreCase = true) ||
                                    task.description.contains(searchTerm, ignoreCase = true)) {
                                    // Add the task to the task list
                                    taskList.add(task)
                                }
                            }
                        }
                        // Notify the adapter that the data has changed
                        taskAdapter.notifyDataSetChanged()

                        // Show a message if no tasks found
                        if (taskList.isEmpty()) {
                            Toast.makeText(this@activity_search_task, "No tasks found", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle possible errors
                        Toast.makeText(this@activity_search_task, "Failed to search tasks: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }
}
