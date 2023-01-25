package com.example.happypig

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.MimeTypeFilter.matches
import androidx.core.widget.addTextChangedListener
import org.w3c.dom.Text
import java.util.Date
import java.util.regex.Pattern
import java.util.regex.Pattern.matches

//회원가입 페이지
class SignUpActivity : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var edtSignUp_nickname: EditText
    lateinit var edtSignUp_id: EditText
    lateinit var edtSignUp_pw: EditText
    lateinit var edtSignUp_pw2: EditText
    lateinit var edtSignUp_email: EditText

    lateinit var btnSignUp_idCheck: Button
    lateinit var btnSignUp_emailCheck: Button
    lateinit var btnSignUp_SignUp: Button

    lateinit var cbSignUp_emailAgree: CheckBox

    lateinit var tvSignUp_readme: TextView
    lateinit var tvSignUp_warning_nickname: TextView
    lateinit var tvSignUp_warning_id: TextView
    lateinit var tvSignUp_warning_pw: TextView
    lateinit var tvSignUp_warning_pw2: TextView
    lateinit var tvSignUp_warning_email: TextView

    lateinit var img_check_nickname: ImageView
    lateinit var img_check_id: ImageView
    lateinit var img_check_pw: ImageView
    lateinit var img_check_pw2: ImageView
    lateinit var img_check_email: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        edtSignUp_nickname = findViewById(R.id.edtSignUp_nickname)
        edtSignUp_id = findViewById(R.id.edtSignUp_id)
        edtSignUp_pw = findViewById(R.id.edtSignUp_pw)
        edtSignUp_pw2 = findViewById(R.id.edtSignUp_pw2)
        edtSignUp_email = findViewById(R.id.edtSignUp_email)

        btnSignUp_idCheck = findViewById(R.id.btnSignUp_idCheck)
        btnSignUp_emailCheck = findViewById(R.id.btnSignUp_emailCheck)
        btnSignUp_SignUp = findViewById(R.id.btnSignUp_SignUp)

        cbSignUp_emailAgree = findViewById(R.id.cbSignUp_emailAgree)

        tvSignUp_readme = findViewById(R.id.tvSignUp_readme)
        tvSignUp_warning_nickname = findViewById(R.id.tvSignUp_warning_nickname)
        tvSignUp_warning_id = findViewById(R.id.tvSignUp_warning_id)
        tvSignUp_warning_pw = findViewById(R.id.tvSignUp_warning_pw)
        tvSignUp_warning_pw2 = findViewById(R.id.tvSignUp_warning_pw2)
        tvSignUp_warning_email = findViewById(R.id.tvSignUp_warning_email)

        img_check_nickname = findViewById(R.id.img_check_nickname)
        img_check_id = findViewById(R.id.img_check_id)
        img_check_pw = findViewById(R.id.img_check_pw)
        img_check_pw2 = findViewById(R.id.img_check_pw2)
        img_check_email = findViewById(R.id.img_check_email)


        var nickFlag: Boolean = false
        var idFlag: Boolean = false
        var pwFlag: Boolean = false
        var pw2Flag: Boolean = false
        var emailFlag: Boolean = false
        var agreeFlag: Boolean = false

        var signUpCheck = SignUpCheck(btnSignUp_SignUp)


        //dbManager 객체 받아오기
        //얘 닫아야됨?
        dbManager = DBManager(this, "guruDB", null, 1)

        //엥 얜 뭐임
        var password: String


        //닉네임 체크

        edtSignUp_nickname.addTextChangedListener {
            //입력값이 있으면 체크
            img_check_nickname.setVisibility(View.VISIBLE)
            nickFlag = true


            //클래스 함수
            signUpCheck.nickFlag = true
            signUpCheck.isRead(nickFlag, idFlag, pwFlag, pw2Flag, emailFlag, agreeFlag)


            //없으면 체크 사라짐
            if (edtSignUp_nickname.length() == 0) {
                img_check_nickname.setVisibility(View.INVISIBLE)
                nickFlag = false


                //클래스 함수
                signUpCheck.nickFlag = false
            }


        }



        //id 중복 확인
        btnSignUp_idCheck.setOnClickListener {
            var id: String = ""

            if (edtSignUp_id.length() == 0) {
                //id 값이 null이라면 토스트 메시지 출력
                //이거 나중에 지우기
                Toast.makeText(this, "아이디를 먼저 입력해주세요", Toast.LENGTH_SHORT).show()

                //경고 문구 출력
                tvSignUp_warning_id.text = "아이디를 먼저 입력해주세요"
                tvSignUp_warning_id.setVisibility(View.VISIBLE)
                idFlag = false


                //클래스 함수
                signUpCheck.idFlag = false

                //입력이 한 글자라도 들어오면 경고 문구 지우기
                edtSignUp_id.addTextChangedListener {
                    tvSignUp_warning_id.setVisibility(View.INVISIBLE)
                }
            } else {
                //id값이 null이 아니라면
                id = edtSignUp_id.text.toString()

                sqlitedb = dbManager.readableDatabase
                var cursor: Cursor
                cursor =
                    sqlitedb.rawQuery("SELECT id FROM personnel WHERE id = '" + id + "';", null)

                if (cursor.moveToNext()) {
                    //이미 존재하는 아이디라면 토스트 메시지를 띄운다
                    Toast.makeText(this, "중복된 아이디입니다", Toast.LENGTH_SHORT).show()

                    //경고 문구 출력
                    tvSignUp_warning_id.text = "중복된 아이디입니다"
                    tvSignUp_warning_id.setVisibility(View.VISIBLE)
                    idFlag = false


                    //클래스 함수
                    signUpCheck.idFlag = false

                    //에디트 텍스트에 입력이 들어오면 경고문구 숨김
                    edtSignUp_id.addTextChangedListener {
                        tvSignUp_warning_id.setVisibility(View.INVISIBLE)
                    }
                } else {
                    //사용 가능한 아이디라면 버튼 글씨가 변경되고 중복 확인 버튼이 비활성화 됨
                    btnSignUp_idCheck.text = "사용 가능"
                    btnSignUp_idCheck.isEnabled = false //버튼 비활성화
                    img_check_id.visibility = View.VISIBLE
                    idFlag = true


                    //클래스 함수
                    signUpCheck.idFlag = true
                    signUpCheck.isRead(nickFlag, idFlag, pwFlag, pw2Flag, emailFlag, agreeFlag)

                    //에디트 텍스트 수정 감지
                    //수정 됐다면 버튼 활성화해서 다시 체크하게끔
                    edtSignUp_id.addTextChangedListener {
                        btnSignUp_idCheck.text = "중복 확인"
                        btnSignUp_idCheck.isEnabled = true
                        img_check_id.visibility = View.INVISIBLE
                        idFlag = false


                        //클래스 함수
                        signUpCheck.idFlag = false
                    }

                }

                cursor.close()
                sqlitedb.close()
            }

        }


        //비밀번호 영문, 숫자 8자리 이상 체크
        val pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,16}$"

        edtSignUp_pw.addTextChangedListener {


            val inputPassword = edtSignUp_pw.text.toString()

            val pattern = Pattern.compile(pwPattern)

            val matcher = pattern.matcher(inputPassword)


            if (!matcher.find()) {
                //비밀번호 양식이 맞지 않는 경우
                tvSignUp_warning_pw.text = "영문, 숫자 조합 8-16자"
                tvSignUp_warning_pw.visibility = View.VISIBLE
                img_check_pw.visibility = View.INVISIBLE
                pwFlag = false


                //클래스 함수
                signUpCheck.pwFlag = false

                //합쳐진 코드 부분
                //비밀번호 확인 부분
                edtSignUp_pw2.addTextChangedListener {
                    //비밀번호 양식이 맞지 않은데 비밀번호 확인을 입력했을 때
                    tvSignUp_warning_pw2.text = "올바른 비밀번호를 먼저 입력해주세요"
                    tvSignUp_warning_pw2.visibility = View.VISIBLE
                    img_check_pw2.visibility = View.INVISIBLE
                    pw2Flag = false

                    //클래스 함수
                    signUpCheck.pwFlag = false

                    if(edtSignUp_pw2.length() == 0){
                        tvSignUp_warning_pw2.visibility = View.INVISIBLE
                    }

                }

                //비밀 번호 입력을 지웠을 때 경구문구 삭제
                if (inputPassword.length == 0) {
                    tvSignUp_warning_pw.visibility = View.INVISIBLE
                    tvSignUp_warning_pw2.visibility = View.INVISIBLE
                }

            } else {
                //비밀번호 양식이 맞는 경우
                img_check_pw.visibility = View.VISIBLE
                tvSignUp_warning_pw.visibility = View.INVISIBLE
                pwFlag = true


                //클래스 함수
                signUpCheck.pwFlag = true
                signUpCheck.isRead(nickFlag, idFlag, pwFlag, pw2Flag, emailFlag, agreeFlag)


                //합쳐진 코드 부분
                //비밀번호 확인 부분
                edtSignUp_pw2.addTextChangedListener {
                    val input = edtSignUp_pw2.text.toString()
                    val password = edtSignUp_pw.text.toString()
                    //윗 줄 password를 inputPassword로 수정해야돼

                    if (input == password && input.length != 0 && password.length != 0 && pwFlag) {
                        //비밀번호가 일치할 때
                        img_check_pw2.visibility = View.VISIBLE
                        pw2Flag = true

                        //클래스 함수
                        signUpCheck.pw2Flag = true
                        signUpCheck.isRead(nickFlag, idFlag, pwFlag, pw2Flag, emailFlag, agreeFlag)

                        //혹시 모르니 경고 문구 삭제
                        tvSignUp_warning_pw.visibility = View.INVISIBLE
                        tvSignUp_warning_pw2.visibility = View.INVISIBLE

                        //비밀번호가 일치했는데 비밀번호 입력을 수정
                        edtSignUp_pw.addTextChangedListener {
                            if (!password.equals(edtSignUp_pw.text)) {
                                tvSignUp_warning_pw2.text = "비밀번호가 일치하지 않습니다."

                                tvSignUp_warning_pw2.visibility = View.VISIBLE
                                img_check_pw2.visibility = View.INVISIBLE

                                signUpCheck.pw2Flag = false
                            }
                        }
                    } else {
                        tvSignUp_warning_pw2.text = "비밀번호가 일치하지 않습니다"
                        tvSignUp_warning_pw2.visibility = View.VISIBLE
                        img_check_pw2.visibility = View.INVISIBLE

                        pw2Flag = false
                        if(edtSignUp_pw2.length() == 0){
                            tvSignUp_warning_pw2.visibility = View.INVISIBLE
                        }


                        //클래스 함수
                        signUpCheck.pw2Flag = true
                        signUpCheck.isRead(nickFlag, idFlag, pwFlag, pw2Flag, emailFlag, agreeFlag)
                    }
                }

            }

        }

        //비밀번호보다 비밀번호 확인을 먼저 입력한 경우
        edtSignUp_pw2.addTextChangedListener {

            //비밀번호를 입력하지 않고 비밀번호 확인을 입력한 경우
            if (edtSignUp_pw.length() == 0) {
                edtSignUp_pw2.addTextChangedListener {
                    tvSignUp_warning_pw2.text = "비밀번호를 먼저 입력해주세요"
                    tvSignUp_warning_pw2.visibility = View.VISIBLE
                    img_check_pw2.visibility = View.INVISIBLE

                    //비밀번호 확인 입력이 삭제되면
                    //경고 메시지 사라짐
                    if (edtSignUp_pw2.length() == 0) {
                        tvSignUp_warning_pw2.visibility = View.INVISIBLE
                    }

                    //비밀번호 입력이 삭제되면 경고메시지를 사라지게끔 하고 싶음
                    /*
                    edtSignUp_pw.addTextChangedListener {
                        if(edtSignUp_pw.length() > 0) {
                            tvSignUp_warning_pw2.visibility = View.INVISIBLE
                        }
                    }*/
                }
            }
        }


        //이메일 중복 확인
        btnSignUp_emailCheck.setOnClickListener {


            if (edtSignUp_email.length() == 0) {
                //email 값이 null이라면 토스트 메시지 출력
                //이거 나중에 지우기
                //Toast.makeText(this, "이메일을 먼저 입력해주세요", Toast.LENGTH_SHORT).show()


                //경고 문구 출력
                tvSignUp_warning_email.text = "이메일을 먼저 입력해주세요"
                tvSignUp_warning_email.setVisibility(View.VISIBLE)

                emailFlag = false


                //클래스 함수
                signUpCheck.emailFlag = false


                //입력이 한 글자라도 들어오면 경고 문구 지우기
                edtSignUp_email.addTextChangedListener {
                    tvSignUp_warning_email.setVisibility(View.INVISIBLE)
                }
            } else {
                //email값이 null이 아니라면
                val inputEmail = edtSignUp_email.text.toString()


                //email 정규식 확인
                val emailPattern =
                    "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"
                val pattern = Pattern.compile(emailPattern)
                val matcher = pattern.matcher(inputEmail)


                if (!matcher.find()) {
                    //이메일 형식이 아님
                    tvSignUp_warning_email.text = "이메일 형식이 아닙니다"
                    tvSignUp_warning_email.visibility = View.VISIBLE
                    emailFlag = false


                    //클래스 함수
                    signUpCheck.emailFlag = false

                    //입력이 한 글자라도 들어오면 경고 문구 지우기
                    edtSignUp_email.addTextChangedListener {
                        tvSignUp_warning_email.setVisibility(View.INVISIBLE)
                    }

                } else {
                    //이메일 형식 만족
                    sqlitedb = dbManager.readableDatabase
                    var cursor: Cursor
                    cursor = sqlitedb.rawQuery(
                        "SELECT email FROM personnel WHERE email = '" + inputEmail + "';",
                        null
                    )

                    if (cursor.moveToNext()) {
                        //이미 존재하는 아이디라면 토스트 메시지를 띄운다
                        Toast.makeText(this, "이미 가입된 이메일입니다", Toast.LENGTH_SHORT).show()

                        //경고 문구 출력
                        tvSignUp_warning_email.text = "이미 가입된 이메일입니다"
                        tvSignUp_warning_email.setVisibility(View.VISIBLE)
                        emailFlag = false


                        //클래스 함수
                        signUpCheck.emailFlag = false

                        //에디트 텍스트에 입력이 들어오면 경고문구 숨김
                        edtSignUp_email.addTextChangedListener {
                            tvSignUp_warning_email.setVisibility(View.INVISIBLE)
                        }
                    } else {
                        //사용 가능한 아이디라면 버튼 글씨가 변경되고 중복 확인 버튼이 비활성화 됨
                        btnSignUp_emailCheck.text = "사용 가능"
                        btnSignUp_emailCheck.isEnabled = false //버튼 비활성화
                        img_check_email.visibility = View.VISIBLE
                        emailFlag = true


                        //클래스 함수
                        signUpCheck.emailFlag = true
                        signUpCheck.isRead(nickFlag, idFlag, pwFlag, pw2Flag, emailFlag, agreeFlag)

                        //에디트 텍스트 수정 감지
                        //수정 됐다면 버튼 활성화해서 다시 체크하게끔
                        edtSignUp_email.addTextChangedListener {
                            btnSignUp_emailCheck.text = "중복 확인"
                            btnSignUp_emailCheck.isEnabled = true
                            img_check_email.visibility = View.INVISIBLE
                            emailFlag = false


                            //클래스 함수
                            signUpCheck.emailFlag = false
                        }

                    }

                    cursor.close()
                    sqlitedb.close()
                }


            }

        }


        //약관 체크
        cbSignUp_emailAgree.setOnCheckedChangeListener { compoundButton, ischecked ->
            if (cbSignUp_emailAgree.isChecked == true) {
                agreeFlag = true


                //클래스 함수
                signUpCheck.agreeFlag = true
                signUpCheck.isRead(nickFlag, idFlag, pwFlag, pw2Flag, emailFlag, agreeFlag)
            } else {
                agreeFlag = false


                //클래스 함수
                signUpCheck.agreeFlag = false
            }
        }


        //이용 약관
        tvSignUp_readme.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("이메일 이용 약관")
            builder.setMessage(R.string.emailAgree)
            builder.setPositiveButton("확인", null)
            builder.show()
        }


        //회원 가입 버튼 클릭
        //디비에 회원 정보 등록
        //홈 화면으로 이동
        btnSignUp_SignUp.setOnClickListener {
            //DB에 등록
            var nickname: String = edtSignUp_nickname.text.toString()
            var id: String = edtSignUp_id.text.toString()
            var pw: String = edtSignUp_pw.text.toString()
            var email: String = edtSignUp_email.text.toString()

            val lv = 0

            //현재 시간
            var day = Date()
            var year = day.year
            var month = day.month
            var date = day.date

            sqlitedb = dbManager.writableDatabase
            sqlitedb.execSQL("INSERT INTO personnel VALUES ('" + id + "', '" + pw + "', '" + nickname + "', '" + email + "', '" + lv + "', 0, " + year + ", " + month + ", " + date + ");")
            sqlitedb.close()

            var dateChangeFlag = false

           //홈 화면으로 전환
            intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dateChange", dateChangeFlag)
            intent.putExtra("id", id)
            startActivity(intent)

            //Toast.makeText(this, "$nickname 님 회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()

        }

        dbManager.close()

    }


}


