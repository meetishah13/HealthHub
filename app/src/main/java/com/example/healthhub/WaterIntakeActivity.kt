package com.example.healthhub

//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import cjh.WaveProgressBarlibrary.WaveProgressBar;
//import com.github.mikephil.charting.animation.Easing
//
//
//class WaterIntakeActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_water_intake)
//    }
//}


import android.app.NotificationManager
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.example.healthhub.fragments.BottomSheetFragment
//import keyur.diwan.project.waterReminder.helpers.AlarmHelper
import com.example.healthhub.helpers.SqliteHelper
import com.example.healthhub.utils.AppUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import params.com.stepprogressview.StepProgressView

//import kotlinx.android.synthetic.main.activity_main.*


class WaterIntakeActivity : AppCompatActivity() {

    private var totalIntake: Int = 0
    private var inTook: Int = 0
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sqliteHelper: SqliteHelper
    private lateinit var dateNow: String
    private var notificStatus: Boolean = false
    private var selectedOption: Int? = null
    private var snackbar: Snackbar? = null
    private var doubleBackToExitPressedOnce = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_intake)

        sharedPref = getSharedPreferences(AppUtils.USERS_SHARED_PREF, AppUtils.PRIVATE_MODE)
        sqliteHelper = SqliteHelper(this)

        totalIntake = sharedPref.getInt(AppUtils.TOTAL_INTAKE, 0)

        if (sharedPref.getBoolean(AppUtils.FIRST_RUN_KEY, true)) {
           startActivity(Intent(this, WalkThroughActivity::class.java))
            finish()
        } else if (totalIntake <= 0) {
            startActivity(Intent(this, InitUserInfoActivity::class.java))
            finish()
        }

        dateNow = AppUtils.getCurrentDate()!!

    }

    fun updateValues() {
        totalIntake = sharedPref.getInt(AppUtils.TOTAL_INTAKE, 0)

        inTook = sqliteHelper.getIntook(dateNow)

        setWaterLevel(inTook, totalIntake)
    }

    override fun onStart() {
        super.onStart()

        val outValue = TypedValue()
        applicationContext.theme.resolveAttribute(
            android.R.attr.selectableItemBackground,
            outValue,
            true
        )
        val btnNotific = findViewById<FloatingActionButton>(R.id.btnNotific)
        notificStatus = sharedPref.getBoolean(AppUtils.NOTIFICATION_STATUS_KEY, true)
//        val alarm = AlarmHelper()
//        val btnNotific = findViewById<FloatingActionButton>(R.id.btnNotific)
//        if (!alarm.checkAlarm(this) && notificStatus) {
//            btnNotific.setImageDrawable(getDrawable(R.drawable.ic_bell))
//            alarm.setAlarm(
//                this,
//                sharedPref.getInt(AppUtils.NOTIFICATION_FREQUENCY_KEY, 30).toLong()
//            )
//        }
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        if (notificStatus) {

            btnNotific.setImageResource(R.drawable.ic_bell)
        } else {
            btnNotific.setImageResource(R.drawable.ic_bell_disabled)
        }

        sqliteHelper.addAll(dateNow, 0, totalIntake)

        updateValues()
        val btnMenu = findViewById<ImageView>(R.id.btnMenu)
        btnMenu.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment(this)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }


        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        fabAdd.setImageResource(R.drawable.ic_plus_solid)
        fabAdd.setOnClickListener{
            if (selectedOption != null) {
                if ((inTook * 100 / totalIntake) <= 140) {
                    if (sqliteHelper.addIntook(dateNow, selectedOption!!) > 0) {
                        inTook += selectedOption!!
                        setWaterLevel(inTook, totalIntake)

                        Snackbar.make(it, "Your water intake was saved...!!", Snackbar.LENGTH_SHORT)
                            .show()

                    }
                } else {
                    Snackbar.make(it, "You already achieved the goal", Snackbar.LENGTH_SHORT).show()
                }
                selectedOption = null

                val tvCustom = findViewById<TextView>(R.id.tvCustom)
                tvCustom.text = "Custom"
                val op50ml = findViewById<LinearLayout>(R.id.op50ml)
                val op100ml = findViewById<LinearLayout>(R.id.op100ml)
                val op150ml = findViewById<LinearLayout>(R.id.op150ml)
                val op200ml = findViewById<LinearLayout>(R.id.op200ml)
                val op250ml = findViewById<LinearLayout>(R.id.op250ml)
                val opCustom = findViewById<LinearLayout>(R.id.opCustom)
                op50ml.background = getDrawable(outValue.resourceId)
                op100ml.background = getDrawable(outValue.resourceId)
                op150ml.background = getDrawable(outValue.resourceId)
                op200ml.background = getDrawable(outValue.resourceId)
                op250ml.background = getDrawable(outValue.resourceId)
                opCustom.background = getDrawable(outValue.resourceId)

                val cardView = findViewById<CardView>(R.id.cardView)
                // remove pending notifications
                val mNotificationManager : NotificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                mNotificationManager.cancelAll()
            } else {
//                YoYo.with(Techniques.Shake)
//                    .duration(700)
//                    .playOn(cardView)
//                Snackbar.make(it, "Please select an option", Snackbar.LENGTH_SHORT).show()
            }
        }

//        btnNotific.setOnClickListener {
//            notificStatus = !notificStatus
//            sharedPref.edit().putBoolean(AppUtils.NOTIFICATION_STATUS_KEY, notificStatus).apply()
//            if (notificStatus) {
//                btnNotific.setImageDrawable(getDrawable(R.drawable.ic_bell))
//                Snackbar.make(it, "Notification Enabled..", Snackbar.LENGTH_SHORT).show()
//                alarm.setAlarm(
//                    this,
//                    sharedPref.getInt(AppUtils.NOTIFICATION_FREQUENCY_KEY, 30).toLong()
//                )
//            } else {
//                btnNotific.setImageDrawable(getDrawable(R.drawable.ic_bell_disabled))
//                Snackbar.make(it, "Notification Disabled..", Snackbar.LENGTH_SHORT).show()
//                alarm.cancelAlarm(this)
//            }
//        }

        val btnStats = findViewById<FloatingActionButton>(R.id.btnStats)
        btnStats.setOnClickListener {
            startActivity(Intent(this, StatsActivity::class.java))
        }
        val op50ml = findViewById<LinearLayout>(R.id.op50ml)
        val op100ml = findViewById<LinearLayout>(R.id.op100ml)
        val op150ml = findViewById<LinearLayout>(R.id.op150ml)
        val op200ml = findViewById<LinearLayout>(R.id.op200ml)
        val op250ml = findViewById<LinearLayout>(R.id.op250ml)
        val opCustom = findViewById<LinearLayout>(R.id.opCustom)

        op50ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            selectedOption = 50

            op50ml.background = getDrawable(R.drawable.option_select_bg)
            op100ml.background = getDrawable(outValue.resourceId)
            op150ml.background = getDrawable(outValue.resourceId)
            op200ml.background = getDrawable(outValue.resourceId)
            op250ml.background = getDrawable(outValue.resourceId)
            opCustom.background = getDrawable(outValue.resourceId)

        }

        op100ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            selectedOption = 100
            op50ml.background = getDrawable(outValue.resourceId)
            op100ml.background = getDrawable(R.drawable.option_select_bg)
            op150ml.background = getDrawable(outValue.resourceId)
            op200ml.background = getDrawable(outValue.resourceId)
            op250ml.background = getDrawable(outValue.resourceId)
            opCustom.background = getDrawable(outValue.resourceId)

        }

        op150ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            selectedOption = 150
            op50ml.background = getDrawable(outValue.resourceId)
            op100ml.background = getDrawable(outValue.resourceId)
            op150ml.background = getDrawable(R.drawable.option_select_bg)
            op200ml.background = getDrawable(outValue.resourceId)
            op250ml.background = getDrawable(outValue.resourceId)
            opCustom.background = getDrawable(outValue.resourceId)

        }

        op200ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            selectedOption = 200
            op50ml.background = getDrawable(outValue.resourceId)
            op100ml.background = getDrawable(outValue.resourceId)
            op150ml.background = getDrawable(outValue.resourceId)
            op200ml.background = getDrawable(R.drawable.option_select_bg)
            op250ml.background = getDrawable(outValue.resourceId)
            opCustom.background = getDrawable(outValue.resourceId)

        }

        op250ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            selectedOption = 250
            op50ml.background = getDrawable(outValue.resourceId)
            op100ml.background = getDrawable(outValue.resourceId)
            op150ml.background = getDrawable(outValue.resourceId)
            op200ml.background = getDrawable(outValue.resourceId)
            op250ml.background = getDrawable(R.drawable.option_select_bg)
            opCustom.background = getDrawable(outValue.resourceId)

        }

        opCustom.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }

            val li = LayoutInflater.from(this)
            val promptsView = li.inflate(R.layout.custom_input_dialog, null)

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setView(promptsView)

            val userInput = promptsView
                .findViewById(R.id.etCustomInput) as TextInputLayout
            val tvCustom = findViewById<TextView>(R.id.tvCustom)
            alertDialogBuilder.setPositiveButton("OK") { dialog, id ->
                val inputText = userInput.editText!!.text.toString()
                if (!TextUtils.isEmpty(inputText)) {
                    tvCustom.text = "${inputText} ml"
                    selectedOption = inputText.toInt()
                }
            }.setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

            op50ml.background = getDrawable(outValue.resourceId)
            op100ml.background = getDrawable(outValue.resourceId)
            op150ml.background = getDrawable(outValue.resourceId)
            op200ml.background = getDrawable(outValue.resourceId)
            op250ml.background = getDrawable(outValue.resourceId)
            opCustom.background = getDrawable(R.drawable.option_select_bg)

        }

    }


    private fun setWaterLevel(inTook: Int, totalIntake: Int) {
        val tvIntook = findViewById<TextView>(R.id.tvIntook)
        val tvTotalIntake = findViewById<TextView>(R.id.tvTotalIntake)
        val intakeProgress = findViewById<StepProgressView>(R.id.intakeProgress)
        val main_activity_parent = findViewById<ConstraintLayout>(R.id.main_activity_parent)
        YoYo.with(Techniques.SlideInDown)
            .duration(500)
            .playOn(tvIntook)
        tvIntook.text = "$inTook"
        tvTotalIntake.text = "/$totalIntake ml"
        val progress = ((inTook / totalIntake.toFloat()) * 100).toInt()
        YoYo.with(Techniques.Pulse)
            .duration(500)
            .playOn(intakeProgress)
        intakeProgress.currentProgress = progress
        if ((inTook * 100 / totalIntake) > 140) {
            Snackbar.make(main_activity_parent, "You achieved the goal", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Snackbar.make(
            this.window.decorView.findViewById(android.R.id.content),
            "Please click BACK again to exit",
            Snackbar.LENGTH_SHORT
        ).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1000)
    }

}
