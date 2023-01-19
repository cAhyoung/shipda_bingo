package com.example.happypig

import android.widget.Button

//회원가입할 때 모든 입력을 알맞게 넣었는지 확인하기 위한 클래스
class SignUpCheck(btn : Button) {
    var nickFlag : Boolean = false
    var idFlag : Boolean = false
    var pwFlag : Boolean = false
    var pw2Flag : Boolean = false
    var emailFlag : Boolean = false
    var agreeFlag : Boolean = false
    lateinit var btn : Button

    init {
        this.btn = btn
    }

    fun isRead (flag1 : Boolean, flag2 : Boolean, flag3 : Boolean, flag4 : Boolean, flag5 : Boolean, flag6 : Boolean) {
        if(flag1 && flag2 && flag3 && flag4 && flag5 && flag6){
            btn.isEnabled = true
        }
    }

}