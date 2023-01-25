package com.example.happypig.mypage

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.happypig.R
import com.example.happypig.mypage.detailpage.AnnounceText

class Announce : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announce)

        // 뒤로가기 (announce activity에서 mypage fragment로 전환)
        val toolbarAnnounce = findViewById<Toolbar>(R.id.toolbarAnnounce)
        setSupportActionBar(toolbarAnnounce)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbarAnnounce.setOnClickListener {

        }

        // 공지사항 버튼 클릭 시 activity 전환
        val announce1 = findViewById<Button>(R.id.announce1)

        announce1.setOnClickListener {
            val intent = Intent(this, AnnounceText::class.java)
            startActivity(intent)

        }
    }
}