package com.example.lista4_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title ="Lista 4"
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment_id)

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.navigation_raz
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_raz -> {
                    navController.navigate(R.id.fragmentPageOne)
                    true
                }
                R.id.navigation_dwa -> {
                    navController.navigate(R.id.fragmentPageTwo)
                    true
                }
                R.id.navigation_trzy -> {
                    navController.navigate(R.id.fragmentPageThree)
                    true
                }
                else -> false
            }
        }

        val drawerNavigationView = findViewById<NavigationView>(R.id.nav_view)
        drawerNavigationView.setupWithNavController(navController)
        val drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawer_layout)

        drawerNavigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_raz -> {
                    navController.navigate(R.id.fragmentPageOne)
                    drawerLayout.closeDrawer(drawerNavigationView)
                    true
                }
                R.id.navigation_dwa -> {
                    navController.navigate(R.id.fragmentPageTwo)
                    drawerLayout.closeDrawer(drawerNavigationView)

                    true
                }
                R.id.navigation_trzy -> {
                    navController.navigate(R.id.fragmentPageThree)
                    drawerLayout.closeDrawer(drawerNavigationView)
                    true
                }
                else -> false
            }
        }

    }
}