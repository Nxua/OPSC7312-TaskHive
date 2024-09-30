package com.example.notifeyetest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class activity_achievements : AppCompatActivity() {

    private lateinit var achievementAdapter: AchievementAdapter
    private lateinit var achievementsList: MutableList<Achievement>
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddAchievement: Button
    private lateinit var btnViewAchievements: Button

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_achievements)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://notifeyetest-default-rtdb.europe-west1.firebasedatabase.app/").reference

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.rvAchievements)
        achievementsList = mutableListOf()
        achievementAdapter = AchievementAdapter(achievementsList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = achievementAdapter

        // Load achievements from Firebase initially
        loadAchievements()

        // Add Achievement button click listener
        btnAddAchievement = findViewById(R.id.btnAddAchievement)
        btnAddAchievement.setOnClickListener {
            startActivity(Intent(this, activity_add_achievement::class.java))
        }

        // View Achievements button click listener
        btnViewAchievements = findViewById(R.id.btnViewAchievements)
        btnViewAchievements.setOnClickListener {
            loadAchievements()  // Load achievements when clicked
        }
    }

    private fun loadAchievements() {
        val userId = auth.currentUser?.uid // Get the current user ID
        if (userId != null) {
            database.child("users").child(userId).child("achievements")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        achievementsList.clear() // Clear existing achievements

                        for (achievementSnapshot in dataSnapshot.children) {
                            val achievement = achievementSnapshot.getValue(Achievement::class.java)
                            if (achievement != null) {
                                achievementsList.add(achievement) // Add the achievement to the list
                            }
                        }

                        achievementAdapter.notifyDataSetChanged() // Notify the adapter of data changes
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(this@activity_achievements, "Failed to load achievements: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }
}
