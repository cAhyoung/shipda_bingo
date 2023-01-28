package com.example.happypig.mypage

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.happypig.DBManager
import com.example.happypig.R
import com.example.happypig.home.HomeActivity

class ChangeNick : AppCompatActivity() {

    lateinit var changeNick : EditText
    lateinit var acceptBtn : Button
    lateinit var cursor : Cursor
    private val id by lazy {
        intent.getStringExtra("id").toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_nick)

        val myHelper = DBManager(this, "guruDB", null, 1)
        var db = myHelper.writableDatabase

        // 닉네임 변경
        changeNick = findViewById(R.id.changeNickEt)
        acceptBtn = findViewById(R.id.acceptBtn)


        acceptBtn.setOnClickListener{
            db.execSQL("UPDATE personnel SET nickname = '" + changeNick.text + "' WHERE id = '" + id + "';")
            db.close()
            Toast.makeText(this, "닉네임이 변경되었습니다", Toast.LENGTH_SHORT).show()
        }


        // Change Nickname용 toolbar
        val toolbarChangeNick = findViewById<Toolbar>(R.id.toolbarChangeNick)
        setSupportActionBar(toolbarChangeNick)

    }
}