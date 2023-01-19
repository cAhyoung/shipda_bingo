package com.example.happypig

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.happypig.databinding.ActivityHomeBinding
import com.example.happypig.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    val homeFragment by lazy { HomeFragment() }  // by lazy : 지연 초기화, 최초 사용 시 초기화
    val challengeFragment by lazy { ChallengeFragment() }
    val mapFragment by lazy { MapFragment() }
    val galleryFragment by lazy { GalleryFragment() }
    val myPageFragment by lazy { MyPageFragment() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)




        initNavigationBar()

    }

    private fun initNavigationBar() {

        var btmNav = binding.btmNavView

        btmNav.run {
            OnNavigationItemSelectedListener {
                when (it.itemId) {
                    btmNav.home -> {
                        changeFragment(homeFragment)
                    }
                    binding.challenge -> {
                        changeFragment(challengeFragment)
                    }
                    binding.map -> {
                        changeFragment(mapFragment)
                    }
                    binding.gallery -> {
                        changeFragment(galleryFragment)
                    }
                    binding.mypage -> {
                        changeFragment(myPageFragment)
                    }
                }
                true

            }
            binding.btmNavView.selectedItemId = binding.home
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer, fragment)
            .commit()
    }
}