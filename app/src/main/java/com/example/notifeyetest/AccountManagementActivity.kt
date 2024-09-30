package com.example.notifeyetest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class AccountManagementActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnUpdateProfile: Button
    private lateinit var btnDeleteAccount: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_management)

        auth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile)
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount)

        // Load current user information
        loadUserInfo()

        // Set up button listeners
        btnUpdateProfile.setOnClickListener { updateProfile() }
        btnDeleteAccount.setOnClickListener { deleteAccount() }
    }

    private fun loadUserInfo() {
        val user = auth.currentUser
        user?.let {
            etEmail.setText(it.email)
        }
    }

    private fun updateProfile() {
        val newEmail = etEmail.text.toString()
        val newPassword = etPassword.text.toString()

        // Update email
        auth.currentUser?.updateEmail(newEmail)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to update email", Toast.LENGTH_SHORT).show()
                }
            }

        // Update password
        if (newPassword.isNotEmpty()) {
            auth.currentUser?.updatePassword(newPassword)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun deleteAccount() {
        auth.currentUser?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Account deleted", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity
                } else {
                    Toast.makeText(this, "Failed to delete account", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
