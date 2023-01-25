package com.example.happypig.mypage

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.happypig.R
import com.example.happypig.home.HomeActivity
import com.example.happypig.mypage.detailpage.AnnounceText

class Announce : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announce)

        // announce용 toolbar
        val toolbarAnnounce = findViewById<Toolbar>(R.id.toolbarAnnounce)
        setSupportActionBar(toolbarAnnounce)

        // 공지사항 버튼 클릭 시 activity 전환
        val announce1 = findViewById<Button>(R.id.announce1)

        announce1.setOnClickListener {
            val intent = Intent(this, AnnounceText::class.java)
            startActivity(intent)

        }
    }
}