package com.example.happypig.mypage

import android.annotation.SuppressLint
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    lateinit var newPwRe : EditText
    lateinit var acceptBtn : Button
    lateinit var rejectPwTv : TextView
    lateinit var notPresentPwTv : TextView
    lateinit var rejectPwReTv : TextView

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
        newPwRe = findViewById(R.id.newPwRe)
        acceptBtn = findViewById(R.id.acceptBtn2)
        rejectPwTv = findViewById(R.id.rejectPwTv)
        rejectPwReTv = findViewById(R.id.rejectPwReTv)
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
            // 첫번째 조건: 현재 비밀번호가 일치하는가? -> 한다
            if(presentPw.text.toString() == dbPw) {
                // 두번째 조건: 새로운 비밀번호 양식이 맞는가? -> 안맞다
                if(!matcher.find()) {
                    rejectPwTv.text = "비밀번호 양식에 맞지 않습니다.(영문+숫자 8~16자)"
                    rejectPwTv.visibility = View.VISIBLE
                    notPresentPwTv.visibility = View.INVISIBLE
                    rejectPwReTv.visibility = View.INVISIBLE

                // 두번째 조건: 새로운 비밀번호 양식이 맞는가? -> 맞다
                } else {
                    // 세번째 조건: 새로운 비번이 현재 비번과 다른가? -> 다르다
                    if(presentPw.text.toString() != newPw.text.toString()){
                        // 네번째 조건: 새로운 비번과 한번 더 작성한 비번이 일치하는가? -> 일치한다
                        if(newPw.text.toString() == newPwRe.text.toString()){
                            rejectPwTv.visibility = View.INVISIBLE
                            notPresentPwTv.visibility = View.INVISIBLE
                            rejectPwReTv.visibility = View.INVISIBLE
                            db.execSQL("UPDATE personnel SET pw = '" + newPw.text.toString() + "' WHERE id = '" + id + "';")
                            db.close()
                            Toast.makeText(this, "비밀번호가 변경되었습니다", Toast.LENGTH_SHORT).show()
                            finish()
                        // 네번째 조건: 새로운 비번과 한번 더 작성한 비번이 일치하는가? -> 일치하지 않는다
                        } else {
                            rejectPwTv.visibility = View.INVISIBLE
                            notPresentPwTv.visibility = View.INVISIBLE
                            rejectPwReTv.visibility = View.VISIBLE
                        }
                    // 세번째 조건: 새로운 비번이 현재 비번과 다른가? -> 다르지 않다
                    } else {
                        rejectPwTv.text = "현재 비밀번호와 새로운 비밀번호가 일치합니다. 비밀번호를 바꾸세요."
                        rejectPwTv.visibility = View.VISIBLE
                        notPresentPwTv.visibility = View.INVISIBLE
                        rejectPwReTv.visibility = View.INVISIBLE
                    }

                }

            // 첫번째 조건: 현재 비밀번호가 일치하는가? -> 안한다
            } else {
                notPresentPwTv.visibility = View.VISIBLE
                rejectPwTv.visibility = View.INVISIBLE
                rejectPwReTv.visibility = View.INVISIBLE
            }

        }

        // Change Pw용 toolbar
        val toolbarChangeNick = findViewById<Toolbar>(R.id.toolbarChangeNick)
        setSupportActionBar(toolbarChangeNick)
    }

}