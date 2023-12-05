package com.example.healthhub

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
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

        //OnClickListener for card views
        var intent:Intent
        findViewById<CardView>(R.id.appointmentSchedule).setOnClickListener {
            intent = Intent(this, ScheduleAppointmentActivity::class.java)
            startActivity(intent)
        }
        findViewById<CardView>(R.id.waterIntake).setOnClickListener {
            intent = Intent(this, WaterIntakeActivity::class.java)
            startActivity(intent)
        }
        findViewById<CardView>(R.id.workout).setOnClickListener {
            intent = Intent(this, WorkoutActivity::class.java)
            startActivity(intent)
        }
        findViewById<CardView>(R.id.healthTips).setOnClickListener {
            intent = Intent(this, HealthTipsAndArticlesActivity::class.java)
            startActivity(intent)
        }
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
            R.id.nav_home ->{
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_scheduleAppointment -> {
                intent = Intent(this, ScheduleAppointmentActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_waterReminder ->{
                intent = Intent(this, WaterIntakeActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_workout -> {
                    intent = Intent(this, WorkoutActivity::class.java)
                    startActivity(intent)
            }
            R.id.nav_article ->{
                intent = Intent(this, HealthTipsAndArticlesActivity::class.java)
                startActivity(intent)
            }
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