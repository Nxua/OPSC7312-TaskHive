package com.example.notifeyetest

import android.os.Bundle
import android.widget.ExpandableListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var expandableListView: ExpandableListView
    private lateinit var expandableListAdapter: CustomExpandableListAdapter
    private lateinit var itemList: List<String>
    private lateinit var subItemMap: HashMap<String, List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        expandableListView = findViewById(R.id.expandableListView)

        // Initialize the drawer toggle
        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize the data
        prepareListData()

        // Initialize the adapter and set it to ExpandableListView
        expandableListAdapter = CustomExpandableListAdapter(this, itemList, subItemMap)
        expandableListView.setAdapter(expandableListAdapter)
    }

    // Prepare the list of items and sub-items
    private fun prepareListData() {
        itemList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

        subItemMap = hashMapOf(
            "Item 1" to listOf("Sub Item 1.1", "Sub Item 1.2"),
            "Item 2" to listOf("Sub Item 2.1", "Sub Item 2.2"),
            "Item 3" to listOf("Sub Item 3.1", "Sub Item 3.2"),
            "Item 4" to listOf("Sub Item 4.1", "Sub Item 4.2"),
            "Item 5" to listOf("Sub Item 5.1", "Sub Item 5.2")
        )
    }

    // Handle the navigation drawer toggle action
    override fun onSupportNavigateUp(): Boolean {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView)
        } else {
            drawerLayout.openDrawer(navigationView)
        }
        return super.onSupportNavigateUp()
    }
}