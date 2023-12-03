package com.example.healthhub

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout:DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toogle = ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.open_nav,R.string.close_nav)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,HomeFragment()).commit()
//            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home ->supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,HomeFragment()).commit()
            R.id.nav_scheduleAppointment ->supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,SchedulerFragment()).commit()
            R.id.nav_waterReminder ->supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,WaterReminderFragment()).commit()
            R.id.nav_workout ->supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,WorkoutFragment()).commit()
            R.id.nav_article ->supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,HealthArticleTipsFragment()).commit()
            R.id.nav_logout ->Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show()

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen((GravityCompat.START))){
            drawerLayout.isDrawerOpen((GravityCompat.START))
        } else{
            onBackPressedDispatcher.onBackPressed()
        }

    }
}