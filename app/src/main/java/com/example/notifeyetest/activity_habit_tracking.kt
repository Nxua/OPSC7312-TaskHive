package com.example.notifeyetest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class activity_habit_tracking : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_tracking)

        // Show a Toast message indicating that the feature is a work in progress
        Toast.makeText(this, "Work in Progress", Toast.LENGTH_SHORT).show()
    }
}
