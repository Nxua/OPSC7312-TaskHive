package com.example.notifeyetest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class activity_add_achievement : AppCompatActivity() {

    private lateinit var etAchievementTitle: EditText
    private lateinit var etAchievementDescription: EditText
    private lateinit var etAchievementDate: EditText
    private lateinit var btnSaveAchievement: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_achievement)

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://notifeyetest-default-rtdb.europe-west1.firebasedatabase.app/").reference

        etAchievementTitle = findViewById(R.id.etAchievementTitle)
        etAchievementDescription = findViewById(R.id.etAchievementDescription)
        etAchievementDate = findViewById(R.id.etAchievementDate)
        btnSaveAchievement = findViewById(R.id.btnSaveAchievement)

        btnSaveAchievement.setOnClickListener {
            saveAchievement()
        }
    }

    private fun saveAchievement() {
        val title = etAchievementTitle.text.toString()
        val description = etAchievementDescription.text.toString()
        val date = etAchievementDate.text.toString()

        if (title.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty()) {
            val userId = auth.currentUser?.uid
            if (userId != null) {
                val achievementId = database.child("users").child(userId).child("achievements").push().key
                if (achievementId != null) {
                    val newAchievement = Achievement(title, description, date)
                    database.child("users").child(userId).child("achievements").child(achievementId).setValue(newAchievement)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Achievement Saved: $title", Toast.LENGTH_SHORT).show()
                            finish() // Close the activity after saving
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to save achievement: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
