package com.example.notifeyetest

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class activity_focus_mode : AppCompatActivity() {

    private lateinit var btnStartFocusMode: Button
    private lateinit var btnStopFocusMode: Button
    private lateinit var tvTimer: TextView
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_focus_mode)

        btnStartFocusMode = findViewById(R.id.btnStartFocusMode)
        btnStopFocusMode = findViewById(R.id.btnStopFocusMode)
        tvTimer = findViewById(R.id.tvTimer)

        btnStartFocusMode.setOnClickListener {
            showTimerInputDialog()
        }

        btnStopFocusMode.setOnClickListener {
            stopTimer()
        }
    }

    private fun showTimerInputDialog() {
        val builder = AlertDialog.Builder(this)
        val input = EditText(this)
        builder.setTitle("Set Timer (minutes)")
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, _ ->
            val timeInput = input.text.toString()
            if (timeInput.isNotEmpty()) {
                val minutes = timeInput.toLongOrNull()
                if (minutes != null) {
                    startTimer(minutes * 60 * 1000)
                } else {
                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun startTimer(duration: Long) {
        timer?.cancel()

        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                tvTimer.text = String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                tvTimer.text = "Time's up!"
                Toast.makeText(this@activity_focus_mode, "Focus session completed!", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun stopTimer() {
        timer?.cancel()
        tvTimer.text = "00:00" // Reset the timer display
        Toast.makeText(this, "Timer stopped", Toast.LENGTH_SHORT).show()
    }
}
