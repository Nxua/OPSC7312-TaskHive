package com.example.notifeyetest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class activity_view_tasks : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var taskList: MutableList<Task> = mutableListOf() // This will be your task list
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_tasks)

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://notifeyetest-default-rtdb.europe-west1.firebasedatabase.app/").reference

        recyclerView = findViewById(R.id.rvTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter and set it to the RecyclerView
        taskAdapter = TaskAdapter(taskList) { /* Handle item click */ }
        recyclerView.adapter = taskAdapter

        // Load tasks from Firebase
        loadTasks()
    }

    private fun loadTasks() {
        val userId = auth.currentUser?.uid // Get the current user ID
        if (userId != null) {
            database.child("users").child(userId).child("tasks")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        taskList.clear() // Clear existing tasks

                        for (taskSnapshot in dataSnapshot.children) {
                            val task = taskSnapshot.getValue(Task::class.java)
                            if (task != null) {
                                taskList.add(task) // Add the task to the list
                            }
                        }

                        taskAdapter.notifyDataSetChanged() // Notify the adapter of data changes
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(this@activity_view_tasks, "Failed to load tasks: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }
}
