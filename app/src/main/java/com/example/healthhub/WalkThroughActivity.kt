package com.example.healthhub

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator


class WalkThroughActivity : AppCompatActivity() {
    private var viewPagerAdapter: WalkThroughAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setContentView(R.layout.activity_walk_through)
        viewPagerAdapter = WalkThroughAdapter(supportFragmentManager)
        val walkThroughPager = findViewById<ViewPager>(R.id.walkThroughPager)
        walkThroughPager.adapter = viewPagerAdapter
        val indicator  = findViewById<DotsIndicator>(R.id.indicator)
        indicator.setViewPager(walkThroughPager)
    }

    override fun onStart() {
        super.onStart()
        val getStarted = findViewById<Button>(R.id.getStarted)
        getStarted.setOnClickListener {

            startActivity(Intent(this, InitUserInfoActivity::class.java))
            finish()

        }
    }

    private inner class WalkThroughAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return 3
        }

        override fun getItem(i: Int): Fragment {

            when (i) {
                0 -> {
                    return WalkThroughOne()
                }

                1 -> {
                    return WalkThroughTwo()
                }

                2 -> {
                    return WalkThroughThree()
                }
                else -> {
                    return WalkThroughOne()
                }
            }

        }
    }


    class WalkThroughOne : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            return inflater.inflate(R.layout.walk_through_one, container, false)

        }
    }

    class WalkThroughTwo : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            return inflater.inflate(R.layout.walk_through_two, container, false)

        }
    }

    class WalkThroughThree : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            return inflater.inflate(R.layout.walk_through_three, container, false)

        }

    }

}
