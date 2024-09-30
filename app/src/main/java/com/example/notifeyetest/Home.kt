package com.example.notifeyetest

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class Home : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var hamburgerMenu: ImageView
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize the drawer layout and navigation view
        drawerLayout = findViewById(R.id.drawerLayout)
        hamburgerMenu = findViewById(R.id.hamburgerMenu)
        navigationView = findViewById(R.id.navigationView)

        // Set up the hamburger menu to open the drawer
        hamburgerMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Handle navigation item selection
        navigationView.setNavigationItemSelectedListener { menuItem ->
            handleNavigationItemSelected(menuItem)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    // Handle the actions when a navigation item is selected
    private fun handleNavigationItemSelected(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_home -> {
                // Handle Home navigation
                // You can display home screen content or stay on the current screen
            }
            R.id.Add -> {
                // Handle Add Task action
                val intent = Intent(this, activity_add_task::class.java)
                startActivity(intent)
            }
            R.id.Search -> {
                // Handle Search Task action
                val intent = Intent(this, activity_search_task::class.java)
                startActivity(intent)
            }
            R.id.View -> {
                // Handle View Tasks action
                val intent = Intent(this, activity_view_tasks::class.java)
                startActivity(intent)
            }
            R.id.Achievements -> {
                // Handle Achievements action
                val intent = Intent(this, activity_achievements::class.java)
                startActivity(intent)
            }
            R.id.Habit -> {
                // Show a notification indicating the feature is a work in progress
                Toast.makeText(this, "Work in Progress", Toast.LENGTH_SHORT).show()
            }
            R.id.Context -> {
                // Handle Context Aware Tasks action
                val intent = Intent(this, activity_context_aware_tasks::class.java)
                startActivity(intent)
            }
            R.id.Focus -> {
                // Handle Focus Mode action
                val intent = Intent(this, activity_focus_mode::class.java)
                startActivity(intent)
            }
            R.id.nav_settings -> {
                // Handle Settings navigation
                val intent = Intent(this, activity_settings::class.java)
                startActivity(intent)
            }
        }
    }
}