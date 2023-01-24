package com.example.happypig.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.happypig.R
import com.example.happypig.challenge.ChallengeFragment
import com.example.happypig.mypage.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    lateinit var btmNav : BottomNavigationView

    val homeFragment by lazy { HomeFragment() }  // by lazy : 지연 초기화, 최초 사용 시 초기화
    val challengeFragment by lazy { ChallengeFragment() }
    val myPageFragment by lazy { MyPageFragment() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        loadFragment(homeFragment)

        btmNav = findViewById(R.id.btmNavView) as BottomNavigationView

        btmNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(homeFragment)
                    true
                }
                R.id.bingo -> {
                    loadFragment(challengeFragment)
                    true
                }
                R.id.mypage -> {
                    loadFragment(myPageFragment)
                    true
                }
                else -> {
                    true
                }
            }
        }



    }

    // fragment 불러오는 함수

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

}

