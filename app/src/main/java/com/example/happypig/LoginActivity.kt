package com.example.happypig

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import java.util.regex.Pattern

//로그인 페이지

class LoginActivity : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var edtLogin_id: EditText
    lateinit var edtLogin_pw: EditText
    lateinit var tvLogin_warningID: TextView
    lateinit var tvLogin_warningPW: TextView
    lateinit var tvLogin_findId: TextView
    lateinit var tvLogin_findPw: TextView
    lateinit var tvLogin_SingUp: TextView
    lateinit var btnLogin_login: Button

    lateinit var ivLogin_id: ImageView
    lateinit var ivLogin_pw: ImageView

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtLogin_id = findViewById(R.id.edtLogin_id)
        edtLogin_pw = findViewById(R.id.edtLogin_pw)
        tvLogin_warningID = findViewById(R.id.tvLogin_warningID)
        tvLogin_warningPW = findViewById(R.id.tvLogin_warningPW)
        tvLogin_findId = findViewById(R.id.tvLogin_findId)
        tvLogin_findPw = findViewById(R.id.tvLogin_findPw)
        tvLogin_SingUp = findViewById(R.id.tvLogin_SignUp)
        btnLogin_login = findViewById(R.id.btnLogin_login)
        ivLogin_id = findViewById(R.id.ivLogin_id)
        ivLogin_pw = findViewById(R.id.ivLogin_pw)


        dbManager = DBManager(this, "guruDB", null, 1)

        //로그인 버튼 클릭
        //id, pw에 null 들어오는지 체크하기
        btnLogin_login.setOnClickListener {
            val user = edtLogin_id.text.toString()
            val pass = edtLogin_pw.text.toString()

            if (user.length == 0 || pass.length == 0) {
                Toast.makeText(this, "회원 정보를 전부 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                sqlitedb = dbManager.readableDatabase
                var cursor: Cursor

                //회원인지 검색
                cursor =
                    sqlitedb.rawQuery("SELECT id FROM personnel WHERE id = '" + user + "';", null)

                if (cursor.moveToNext()) {
                    //존재하는 회원
                    cursor.close()

                    //아이디에 맞는 비밀번호가 존재하는지 확인
                    cursor = sqlitedb.rawQuery(
                        "SELECT pw FROM personnel WHERE id = '" + user + "';",
                        null
                    )
                    if (cursor.moveToNext()) {

                        val dbPW = cursor.getString(cursor.getColumnIndex("pw")).toString()

                        if (pass == dbPW) {
                            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                            //홈 화면으로 액티비티 전혼하기
                            //intent에 id 값 넣어서
                        } else {
                            Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                        }

                        cursor.close()

                    } else {
                        cursor.close()
                    }


                    sqlitedb.close()


                } else {
                    //존재하지 않는 회원
                    cursor.close()

                    Toast.makeText(this, "회원이 아닙니다", Toast.LENGTH_SHORT).show()
                }

                sqlitedb.close()


            }
        }


        //아이디 찾기
        tvLogin_findId.setOnClickListener {
            val intent = Intent(this, FindIdActivity::class.java)
            startActivity(intent)
        }


        //비밀번호 찾기
        tvLogin_findPw.setOnClickListener {
            val intent = Intent(this, FindPwActivity::class.java)
            startActivity(intent)
        }


        //회원가입
        //SignUpActivity로 이동
        tvLogin_SingUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


    }


}