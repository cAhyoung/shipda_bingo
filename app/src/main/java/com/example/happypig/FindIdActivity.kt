package com.example.happypig

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import java.util.regex.Pattern

class FindIdActivity : AppCompatActivity() {


    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var edtFindId_email : EditText
    lateinit var edtFindId_num : EditText

    lateinit var btnFindId_sendEmail : Button
    lateinit var btnFindId_authenticate : Button
    lateinit var btnFindId_findId : Button

    lateinit var tvFindId_Id : TextView
    lateinit var tvFindId_warningEmail : TextView
    lateinit var tvFindId_warningNum : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_id)

        edtFindId_email = findViewById(R.id.edtFindId_email)
        edtFindId_num = findViewById(R.id.edtFindId_num)
        btnFindId_sendEmail = findViewById(R.id.btnFindId_sendEmail)
        btnFindId_authenticate = findViewById(R.id.btnFindId_authenticate)
        btnFindId_findId = findViewById(R.id.btnFindId_findId)
        tvFindId_Id = findViewById(R.id.tvFindId_Id)
        tvFindId_warningEmail = findViewById(R.id.tvFindId_warningEmail)
        tvFindId_warningNum = findViewById(R.id.tvFindId_warningNum)

        dbManager = DBManager(this, "guruDB", null, 1)


        //인증번호 전송

        //입력값이 들어오면 버튼 활성화
        edtFindId_email.addTextChangedListener {
            if(edtFindId_email.text.toString() != ""){
                btnFindId_sendEmail.isEnabled = true
            }
            else {
                btnFindId_sendEmail.isEnabled = false
            }
        }


        btnFindId_sendEmail.setOnClickListener {
            val inputEmail = edtFindId_email.text.toString()
            
            //이메일 형식 검사
            val emailPattern =
                "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"
            val pattern = Pattern.compile(emailPattern)
            val matcher = pattern.matcher(inputEmail)
            
            if(!matcher.find()) {
                //이메일 형식이 아님
                tvFindId_warningEmail.text = "이메일을 정확히 입력해주세요."
                tvFindId_warningEmail.visibility = View.VISIBLE
                
                //이메일 입력을 모두 지우면 경고 문구 사라짐
                edtFindId_email.addTextChangedListener { 
                    if (edtFindId_email.text.toString().length == 0){
                        tvFindId_warningEmail.visibility = View.INVISIBLE
                    }
                }
            }
            else {
                //이메일 형식 만족
                //디비에 이메일이 존재하는지 확인
                sqlitedb = dbManager.readableDatabase
                var cursor : Cursor
                cursor = sqlitedb.rawQuery("SELECT email FROM personnel WHERE email = '" + inputEmail + "';",null)

                if(cursor.moveToNext()){
                    //디비에 존재하는 이메일
                    //이메일로 인증 번호 전송
                    val emailAddress = inputEmail
                    val title = "[빙고 챌린지] 아이디 찾기 인증번호입니다"

                    val randNum = (1000..9999).random()
                    val content = "아이디 찾기 인증번호는 $randNum 입니다."

                    val intent = Intent(Intent.ACTION_SENDTO)
                        .apply {
                            type = "text/plain"
                            data = Uri.parse("mailto:")

                            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                            putExtra(Intent.EXTRA_SUBJECT, title)
                            putExtra(Intent.EXTRA_TEXT, content)
                        }

                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(Intent.createChooser(intent, "메일 전송하기"))
                        Toast.makeText(this, "메일이 전송되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "메일을 전송할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                    
                }
                else {
                    //존재하지 않는 이메일
                    tvFindId_warningEmail.text = "가입되지 않은 이메일입니다."
                    tvFindId_warningEmail.visibility = View.VISIBLE

                    //이메일 입력을 모두 지우면 경고 문구 사라짐
                    edtFindId_email.addTextChangedListener {
                        if (edtFindId_email.text.toString().length == 0){
                            tvFindId_warningEmail.visibility = View.INVISIBLE
                        }
                    }
                }
                
                cursor.close()
                sqlitedb.close()
            }

        }




    }

}