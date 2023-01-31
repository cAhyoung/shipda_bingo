package com.example.happypig.login

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.happypig.DBManager
import com.example.happypig.R
import java.util.regex.Pattern

class FindPwActivity : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var edtFindPw_id : EditText
    lateinit var edtFindPw_email : EditText

    lateinit var tvFindPw_warningId : TextView
    lateinit var tvFindPw_warningEmail : TextView
    lateinit var tvFindPw_pw : TextView

    lateinit var layoutFindPw_layout : LinearLayout

    lateinit var btnFindPw_findPw : Button

    @SuppressLint("Range", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_pw)

        edtFindPw_id = findViewById(R.id.edtFindPw_id)
        edtFindPw_email = findViewById(R.id.edtFindPw_email)
        tvFindPw_warningId = findViewById(R.id.tvFindPw_warningId)
        tvFindPw_warningEmail = findViewById(R.id.tvFindPw_warningEmail)
        btnFindPw_findPw = findViewById(R.id.btnFindPw_findPw)

        tvFindPw_pw =findViewById(R.id.tvFindPw_pw)
        layoutFindPw_layout = findViewById(R.id.layoutFindPw_layout)

        var idFlag = false
        var emailFlag = false

        dbManager = DBManager(this, "guruDB", null, 1)

        //아이디 입력이 변경되는지 체크하는 리스너
        edtFindPw_id.addTextChangedListener {
            //아이디가 먼저 입력되고 이메일이 입력되는 경우
            //이메일 입력이 변경되는지 체크하는 리스너
            edtFindPw_email.addTextChangedListener{
                if (edtFindPw_id.text.length != 0 && edtFindPw_email.text.length != 0) {
                    btnFindPw_findPw.isEnabled = true
                }
                else {
                    btnFindPw_findPw.isEnabled = false
                }

            }
        }

        //이메일 입력이 변경되는지 체크하는 리스너
        edtFindPw_email.addTextChangedListener {
            //이메일이 먼저 입력되고 아이디가 입력되는 경우
            //아이디 입력이 변경되는지 체크하는 리스너
            edtFindPw_id.addTextChangedListener{
                if (edtFindPw_id.text.length != 0 && edtFindPw_email.text.length != 0) {
                    btnFindPw_findPw.isEnabled = true
                }
                else {
                    btnFindPw_findPw.isEnabled = false
                }

            }
        }

        //비밀번호 찾기 버튼 클릭 이벤트
        btnFindPw_findPw.setOnClickListener {
            val inputId = edtFindPw_id.text.toString()
            val inputEmail = edtFindPw_email.text.toString()

            //아이디 검사
            sqlitedb = dbManager.readableDatabase
            var cursor : Cursor
            cursor = sqlitedb.rawQuery("SELECT id FROM personnel WHERE id = '" + inputId + "';",null)
            
            if (cursor.moveToNext()) {
                idFlag = true
            }
            else {
                //존재하지 않는 id
                tvFindPw_warningId.text = "존재하지 않는 Id입니다."
                tvFindPw_warningId.visibility = View.VISIBLE

                //이메일 입력을 모두 지우면 경고 문구 사라짐
                edtFindPw_id.addTextChangedListener {
                    if (edtFindPw_id.text.toString().length == 0){
                        tvFindPw_warningId.visibility = View.INVISIBLE
                    }
                }
            }

            cursor.close()
            sqlitedb.close()
            
            if (idFlag){
                //유효한 id일 때만 이메일 검사

                //이메일 형식 검사
                val emailPattern =
                    "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"
                val pattern = Pattern.compile(emailPattern)
                val matcher = pattern.matcher(inputEmail)

                if (!matcher.find()) {
                    //이메일 형식이 아님
                    tvFindPw_warningEmail.text = "이메일을 정확히 입력해주세요."
                    tvFindPw_warningEmail.visibility = View.VISIBLE

                    //이메일 입력을 모두 지우면 경고 문구 사라짐
                    edtFindPw_email.addTextChangedListener {
                        if (edtFindPw_email.text.toString().length == 0){
                            tvFindPw_warningEmail.visibility = View.INVISIBLE
                        }
                    }
                }
                else {
                    //이메일 형식 만족
                    //디비에 이메일이 존재하는지 확인
                    sqlitedb = dbManager.readableDatabase
                    var cursor : Cursor
                    cursor = sqlitedb.rawQuery("SELECT email FROM personnel WHERE id = '" + inputId + "';",null)

                    if (cursor.moveToNext()) {
                        //이메일 존재
                        //input 이메일과 같은지 확인
                        val emailAddr = cursor.getString(cursor.getColumnIndex("email")).toString()

                        if ( inputEmail.equals(emailAddr)) {
                            emailFlag = true
                        }
                        else {
                            //이메일 틀림
                            tvFindPw_warningEmail.text = "아이디와 이메일이 일치하지 않습니다."
                            tvFindPw_warningEmail.visibility = View.VISIBLE

                            //이메일 입력을 모두 지우면 경고 문구 사라짐
                            edtFindPw_email.addTextChangedListener {
                                if (edtFindPw_email.text.toString().length == 0){
                                    tvFindPw_warningEmail.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                    else {
                        //존재하지 않는 이메일
                        tvFindPw_warningEmail.text = "가입되지 않은 이메일입니다."
                        tvFindPw_warningEmail.visibility = View.VISIBLE

                        //이메일 입력을 모두 지우면 경고 문구 사라짐
                        edtFindPw_email.addTextChangedListener {
                            if (edtFindPw_email.text.toString().length == 0){
                                tvFindPw_warningEmail.visibility = View.INVISIBLE
                            }
                        }
                    }

                    cursor.close()
                    sqlitedb.close()
                }
            }

            if (idFlag && emailFlag) {
                //유효한 아이디와 이메일일 때
                //비밀번호 출력
                sqlitedb = dbManager.readableDatabase
                var cursor : Cursor
                cursor = sqlitedb.rawQuery("SELECT pw FROM personnel WHERE id = '" + inputId + "';",null)
                if (cursor.moveToNext()) {
                    val pw = cursor.getString(cursor.getColumnIndex("pw")).toString()
                    tvFindPw_pw.text = pw
                    layoutFindPw_layout.visibility = View.VISIBLE
                }
                else {
                    Toast.makeText(this,"비밀번호 찾기 실패",Toast.LENGTH_SHORT).show()
                }

                cursor.close()
                sqlitedb.close()

                edtFindPw_id.addTextChangedListener {
                    layoutFindPw_layout.visibility = View.INVISIBLE
                    idFlag = false
                    emailFlag = false
                }
                edtFindPw_email.addTextChangedListener {
                    layoutFindPw_layout.visibility = View.INVISIBLE
                    idFlag = false
                    emailFlag = false
                }
            }




        }
    }
}