package com.example.happypig.mypage

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.happypig.DBManager
import com.example.happypig.R



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

        // 버튼 클릭 시 db 업데이트
        // 마이페이지에 바로 업데이트 되는 부분은 아직 구현하지 못함
        acceptBtn.setOnClickListener{
            db.execSQL("UPDATE personnel SET nickname = '" + changeNick.text + "' WHERE id = '" + id + "';")
            db.close()
            Toast.makeText(this, "닉네임이 변경되었습니다", Toast.LENGTH_SHORT).show()
            finish()

        }


        // Change Nickname용 toolbar
        val toolbarChangeNick = findViewById<Toolbar>(R.id.toolbarChangeNick)
        setSupportActionBar(toolbarChangeNick)

    }
}