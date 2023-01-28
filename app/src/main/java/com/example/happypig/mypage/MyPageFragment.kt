package com.example.happypig.mypage


import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.happypig.DBManager
import com.example.happypig.R
import com.example.happypig.home.HomeActivity
import com.example.happypig.login.LoginActivity
import org.w3c.dom.Text


class MyPageFragment : Fragment() {

    // 상단 노출 정보
    lateinit var nickTv : TextView
    lateinit var lvTv: TextView
    lateinit var nick : String
    var lv : Int = 0

    // 내정보
    lateinit var btnMemberInfo : Button
    lateinit var btnChangeNick : Button
    lateinit var btnChangePw : Button
    lateinit var btnLogout : Button
    lateinit var btnDeleteInfo : Button

    // 기타
    lateinit var btnHelp : Button
    lateinit var btnAnnounce : Button

    // 디비 접속 수정
    lateinit var cursor : Cursor
    private var id : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString("id")
        }


    }

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_page, container, false)
        val hActivity = activity as HomeActivity
        val myHelper = DBManager(hActivity, "guruDB", null, 1)

        // 상단 노출 정보
        var db = myHelper.readableDatabase
        var cursor = db.rawQuery("SELECT * FROM personnel WHERE id = '" + id + "';",null)
        if (cursor.moveToNext()) {
            nick = cursor.getString(cursor.getColumnIndex("nickname")).toString()
            lv = cursor.getString(cursor.getColumnIndex("lv")).toInt()
        }
        cursor.close()
        db.close()

        nickTv = view.findViewById(R.id.nickTv)
        lvTv = view.findViewById(R.id.lvTv)

        nickTv.text = "$nick"
        lvTv.text = "Lv.${lv.toString()}"

        // my info button action
        btnMemberInfo = view.findViewById(R.id.btnMemberInfo)
        btnChangeNick = view.findViewById(R.id.btnChangeNick)
        btnChangePw = view.findViewById(R.id.btnChangePw)
        btnLogout = view.findViewById(R.id.btnLogOut)
        btnDeleteInfo = view.findViewById(R.id.btnDeleteInfo)




        // 회원 정보 확인하기
        btnMemberInfo.setOnClickListener {
            val intent = Intent(getActivity(), MemberInfo::class.java)
            intent.putExtra("id", id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)

        }

        // 닉네임 변경하기
        btnChangeNick.setOnClickListener {
            val intent = Intent(view.context, ChangeNick::class.java)
            intent.putExtra("id", id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)


        }

        // 비밀번호 변경하기
        btnChangePw.setOnClickListener {
            val intent = Intent(getActivity(), ChangePw::class.java)
            intent.putExtra("id", id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)

        }

        // Dialog 띄우기: log out
        btnLogout.setOnClickListener {
            val dialog = AlertDialog.Builder(hActivity)
            dialog.setTitle("로그아웃")
            dialog.setMessage("로그아웃하시겠습니까?")
            dialog.setPositiveButton("예", DialogInterface.OnClickListener{dialog, _ ->
                val intent = Intent(view.context, LoginActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            })

            dialog.setNegativeButton("아니요", null)
            dialog.show()


        }
        // Dialog 띄우기: 탈퇴
        btnDeleteInfo.setOnClickListener {
            val dialog2 = AlertDialog.Builder(hActivity)
            dialog2.setTitle("회원 탈퇴")
            dialog2.setMessage("회원 탈퇴하시겠습니까?")
            dialog2.setPositiveButton("예", DialogInterface.OnClickListener{dialog, _ ->
                val myHelper = DBManager(hActivity, "guruDB", null, 1)
                var db = myHelper.writableDatabase
                db.execSQL("DELETE FROM personnel WHERE id = '" + id + "';")
                db.close()
                val intent = Intent(view.context, LoginActivity::class.java)
                startActivity(intent)
            })

            dialog2.setNegativeButton("아니요", null)
            dialog2.show()

        }

        // else action
        btnHelp = view.findViewById(R.id.btnHelp)
        btnAnnounce = view.findViewById(R.id.btnAnnounce)

        // 도움말 액티비티 띄우기 -> 앱 사용 설명서
        btnHelp.setOnClickListener {
            val intent = Intent(getActivity(), Help::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        // 앱 업데이트 등 공지사항을 볼 수 있는 액티비티 띄우기
        btnAnnounce.setOnClickListener {
            val intent = Intent(getActivity(), Announce::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        return view

    }

}