package com.example.happypig.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.happypig.DBManager
import com.example.happypig.R
import com.example.happypig.home.HomeActivity
import com.example.happypig.tutorial.tutorialActivity
import java.util.*

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


        //최초 실행 여부 판단
        var pref = getSharedPreferences("checkFirst", Activity.MODE_PRIVATE)
        var checkFrist = pref.getBoolean("checkFirst",false)

        //false일 경우 최초 실행 -> 튜토리얼 실행
        if(!checkFrist) {
            var editor = pref.edit()
            editor.putBoolean("checkFirst", true)
            editor.apply()
            finish()

            intent = Intent(this, tutorialActivity::class.java)
            startActivity(intent)

        }

        //접속 기록이 있으면 아이디, 비밀번호값 가져오기
        loadData()

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
                        cursor.close()
                        sqlitedb.close()

                        if (pass == dbPW) {
                            //로그인 정보 저장 for 자동 로그인
                            saveData(user, pass)

                            //지난 접속 기록 가져오기
                            var dbYear : Int = 0
                            var dbMonth : Int = 0
                            var dbDate : Int = 0
                            sqlitedb = dbManager.readableDatabase
                            cursor = sqlitedb.rawQuery("SELECT year, month, date FROM personnel WHERE id = '" + user + "';",null)
                            if(cursor.moveToNext()){
                                dbYear = cursor.getInt(cursor.getColumnIndex("year"))
                                dbMonth = cursor.getInt(cursor.getColumnIndex("month"))
                                dbDate = cursor.getInt(cursor.getColumnIndex("date"))
                            }
                            cursor.close()
                            sqlitedb.close()

                            //앱에 접속한 시간 디비에 업데이트 하기
                            var year = Date().year
                            var month = Date().month
                            var date = Date().date
                            var dateChangeFlag = false

                            sqlitedb = dbManager.writableDatabase
                            sqlitedb.execSQL("UPDATE personnel SET year = " + year + ", month = " + month + ", date = " + date + " WHERE id = '" + user + "';")
                            sqlitedb.close()

                            //만보기 초기화를 위해 날짜가 변경되었는지 체크하기
                            if(date > dbDate){
                                dateChangeFlag = true
                            }
                            else if (month > dbMonth) {
                                dateChangeFlag = true
                            }
                            else if (year > dbYear) {
                                dateChangeFlag = true
                            }

                            intent = Intent(this, HomeActivity::class.java)
                            intent.putExtra("id", user)
                            intent.putExtra("dateChange", dateChangeFlag)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                        }

                        cursor.close()

                    } else {
                        cursor.close()
                        sqlitedb.close()
                    }


                } else {
                    //존재하지 않는 회원
                    cursor.close()
                    sqlitedb.close()
                    Toast.makeText(this, "회원이 아닙니다", Toast.LENGTH_SHORT).show()
                }




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

    private fun saveData(id : String, pw : String) {
        var pref = this.getPreferences(0)
        var editor = pref.edit()

        editor.putString("KEY_id", edtLogin_id.text.toString()).apply()
        editor.putString("KEY_pw", edtLogin_pw.text.toString()).apply()
    }

    private fun loadData() {
        var pref = this.getPreferences(0)
        var id = pref.getString("KEY_id", "")
        var pw = pref.getString("KEY_pw", "")

        if (id!!.length != 0 && pw!!.length != 0) {
            edtLogin_id.setText(id)
            edtLogin_pw.setText(pw)
        }

    }


}