package com.example.healthhub

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.util.regex.Pattern

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var auth: FirebaseAuth
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
        // Retrieve username from the Intent
        val username = intent.getStringExtra("USERNAME_KEY")

        // Extract part ahead of '@' symbol
        val extractedUsername = extractUsername(username)

        // Update the TextView with the extracted username
        val usernameTextView = findViewById<TextView>(R.id.userText)
        usernameTextView.text = "Welcome, $extractedUsername!" // Customize the text as needed


    }
    private fun extractUsername(email: String?): String {
        if (email != null) {
            val pattern = Pattern.compile("^(.+)@.+")
            val matcher = pattern.matcher(email)

            if (matcher.matches()) {
                return matcher.group(1) ?: "Invalid email format"
            }
        }
        return "Invalid email format"
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        auth = Firebase.auth
        when(item.itemId){
//            R.id.nav_home ->supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container,HomeFragment()).commit()
//            R.id.nav_scheduleAppointment ->supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container,SchedulerFragment()).commit()
//            R.id.nav_waterReminder ->supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container,WaterReminderFragment()).commit()
//            R.id.nav_workout ->supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container,WorkoutFragment()).commit()
//            R.id.nav_article ->supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container,HealthArticleTipsFragment()).commit()
            R.id.nav_logout ->{
                auth.signOut()

                // Check user signed-in status and navigate accordingly
                checkUserSignedIn()

                // Show a toast message or any other feedback if needed
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
            }


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
    private fun checkUserSignedIn() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}