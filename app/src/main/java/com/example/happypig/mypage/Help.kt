package com.example.happypig.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.happypig.R

class Help : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)


        // 도움말 toolbar
        val toolbarAnnounce = findViewById<Toolbar>(R.id.toolbarHelp)
        setSupportActionBar(toolbarAnnounce)
    }
}