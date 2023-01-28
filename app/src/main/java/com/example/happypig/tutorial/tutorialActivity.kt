package com.example.happypig.tutorial

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.happypig.R
import com.google.android.material.tabs.TabLayout

class tutorialActivity : AppCompatActivity() {

    lateinit var viewPager : ViewPager
    lateinit var tabLayout : TabLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabDots)
        tabLayout.setupWithViewPager(viewPager, true)

        getFrags()

    }

    private fun getFrags() {
        val fragments = ArrayList<Fragment>()
        fragments.add(bingoTutorialFragment())
        fragments.add(homeTutorialFragment())
        fragments.add(homeTutorialFragment2())

        val adapter = MyAdapter(supportFragmentManager)
        adapter.updateFragments(fragments)
        viewPager.adapter = adapter
    }
}