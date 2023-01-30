package com.example.happypig.mypage

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.happypig.DBManager
import com.example.happypig.R


class MemberInfo : AppCompatActivity() {

    lateinit var idTv : TextView
    lateinit var emailTv : TextView
    lateinit var nickTv : TextView
    lateinit var lvTv: TextView

    lateinit var nick : String
    lateinit var email : String
    var lv : Int = 0

    private val id by lazy { intent.getStringExtra("id") as String }

    @SuppressLint("MissingInflatedId", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_info)

        val myHelper = DBManager(this, "guruDB", null, 1)
        var db = myHelper.readableDatabase

        idTv = findViewById(R.id.idTv)
        emailTv = findViewById(R.id.emailTv)
        nickTv = findViewById(R.id.nickTv)
        lvTv = findViewById(R.id.lvTv)

        // db에서 각 정보 가져오기
        val cursor = db.rawQuery("SELECT * FROM personnel WHERE id = '" + id + "';",null)
        if (cursor.moveToNext()) {

            email = cursor.getString(cursor.getColumnIndex("email")).toString()
            nick = cursor.getString(cursor.getColumnIndex("nickname")).toString()
            lv = cursor.getString(cursor.getColumnIndex("lv")).toInt()

        }
        cursor.close()

        // textview에 보여주기
        idTv.text = "아이디: $id"
        emailTv.text = "이메일: $email"
        nickTv.text = "닉네임: $nick"
        lvTv.text = "레벨: ${lv}"

        // memberinfo용 toolbar
        val toolbarChangeNick = findViewById<Toolbar>(R.id.toolbarMemberInfo)
        setSupportActionBar(toolbarChangeNick)

    }
}