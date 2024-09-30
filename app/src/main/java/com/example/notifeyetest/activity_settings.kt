package com.example.notifeyetest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class activity_settings : AppCompatActivity() {
    private lateinit var rvSettings: RecyclerView
    private lateinit var settingsAdapter: SettingsAdapter
    private val settingsList = listOf("Notifications", "Account Management", "Privacy Settings", "Theme", "About Us")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvSettings = findViewById(R.id.rvSettings)
        rvSettings.layoutManager = LinearLayoutManager(this)

        settingsAdapter = SettingsAdapter(settingsList) { setting ->
            handleSettingClick(setting)
        }
        rvSettings.adapter = settingsAdapter
    }

    private fun handleSettingClick(setting: String) {
        when (setting) {
            "Notifications" -> {
                // Show a dialog to toggle notifications
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Notifications")
                builder.setMessage("Enable or disable notifications?")
                builder.setPositiveButton("Enable") { dialog, _ ->
                    // Code to enable notifications
                    Toast.makeText(this, "Notifications enabled", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                builder.setNegativeButton("Disable") { dialog, _ ->
                    // Code to disable notifications
                    Toast.makeText(this, "Notifications disabled", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                builder.show()
            }
            "Account Management" -> {
                val intent = Intent(this, AccountManagementActivity::class.java)
                startActivity(intent)
            }
            "Privacy Settings" -> {
                // Handle Privacy Settings
            }
            "Theme" -> {
                // Show a dialog to choose a theme
                val themes = arrayOf("Light", "Dark")
                AlertDialog.Builder(this)
                    .setTitle("Choose Theme")
                    .setItems(themes) { _, which ->
                        when (which) {
                            0 -> {
                                // Set light theme
                                setTheme(R.style.AppTheme_Light)
                                Toast.makeText(this, "Light theme applied", Toast.LENGTH_SHORT).show()
                            }
                            1 -> {
                                // Set dark theme
                                setTheme(R.style.AppTheme_Dark)
                                Toast.makeText(this, "Dark theme applied", Toast.LENGTH_SHORT).show()
                            }
                        }
                        recreate() // Restart the activity to apply theme changes
                    }
                    .show()
            }
            "About Us" -> {
                // Handle About Us
            }
        }
    }
}
