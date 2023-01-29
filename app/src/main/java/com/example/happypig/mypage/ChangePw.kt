package com.example.happypig.mypage

import android.annotation.SuppressLint
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.happypig.DBManager
import com.example.happypig.R
import com.google.android.gms.common.internal.Objects.ToStringHelper
import java.util.regex.Pattern

class ChangePw : AppCompatActivity() {

    lateinit var presentPw : EditText
    lateinit var newPw : EditText
    lateinit var acceptBtn : Button
    lateinit var rejectPwTv : TextView
    lateinit var notPresentPwTv : TextView
    lateinit var dbPw : String
    lateinit var cursor : Cursor

    private val id by lazy {
        intent.getStringExtra("id").toString()
    }

    @SuppressLint("MissingInflatedId", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pw)

        val myHelper = DBManager(this, "guruDB", null, 1)
        var db = myHelper.writableDatabase

        presentPw = findViewById(R.id.presentPw)
        newPw = findViewById(R.id.newPw)
        acceptBtn = findViewById(R.id.acceptBtn2)
        rejectPwTv = findViewById(R.id.rejectPwTv)
        notPresentPwTv = findViewById(R.id.notPresentPwTv)

        cursor = db.rawQuery("SELECT pw FROM personnel WHERE id = '" + id + "';",null)
        if (cursor.moveToNext()) {
            dbPw = cursor.getString(cursor.getColumnIndex("pw")).toString()
        }
        cursor.close()


        // pw 양식을 맞췄는가
        val pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,16}$"

        val pattern = Pattern.compile(pwPattern)




        acceptBtn.setOnClickListener {
            val matcher = pattern.matcher(newPw.text.toString())

            if(presentPw.text.toString() == dbPw) {
                if(!matcher.find()) {
                    rejectPwTv.visibility = View.VISIBLE
                    notPresentPwTv.visibility = View.INVISIBLE
                } else {
                    rejectPwTv.visibility = View.INVISIBLE
                    notPresentPwTv.visibility = View.INVISIBLE
                    db.execSQL("UPDATE personnel SET pw = '" + newPw.text.toString() + "' WHERE id = '" + id + "';")
                    db.close()
                    Toast.makeText(this, "비밀번호가 변경되었습니다", Toast.LENGTH_SHORT).show()
                }
            } else {
                if(!matcher.find()) {
                    rejectPwTv.visibility = View.VISIBLE
                    notPresentPwTv.visibility = View.VISIBLE
                } else {
                    rejectPwTv.visibility = View.INVISIBLE
                    notPresentPwTv.visibility = View.VISIBLE
                }
            }

        }

        // Change Pw용 toolbar
        val toolbarChangeNick = findViewById<Toolbar>(R.id.toolbarChangeNick)
        setSupportActionBar(toolbarChangeNick)
    }


}