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
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this);
        data class Message(
            var sender: String? = null,
            var text: String? = null
        )
        // Step 3: Write data to the Realtime Database
        val databaseReference = FirebaseDatabase.getInstance().getReference("messages")
        // Create an instance of the Message class
        val message = Message(sender = "John", text = "Hello, Firebase!")

        // Push data to a new child node with a unique key
        val newMessageReference = databaseReference.push()
        newMessageReference.setValue(message)

        // Alternatively, if you have a specific key you want to use, you can set the value directly
        val specificMessageReference = databaseReference.child("yourSpecificKey")
        specificMessageReference.setValue(message)

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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        findViewById<CardView>(R.id.waterIntake).setOnClickListener {
            intent = Intent(this, WaterIntakeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        }
        findViewById<CardView>(R.id.workout).setOnClickListener {
            intent = Intent(this, WorkoutActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.scale_down, R.anim.scale_up)

        }
        findViewById<CardView>(R.id.healthTips).setOnClickListener {
            intent = Intent(this, HealthTipsAndArticlesActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)

        }
        findViewById<CardView>(R.id.locateUs).setOnClickListener {
            intent = Intent(this, LocateUsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

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
            R.id.nav_locate_us ->{
                intent = Intent(this, LocateUsActivity::class.java)
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