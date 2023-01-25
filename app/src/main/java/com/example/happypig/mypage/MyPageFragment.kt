package com.example.happypig.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.happypig.R
import com.example.happypig.databinding.FragmentHomeBinding
import com.example.happypig.databinding.FragmentMyPageBinding


class MyPageFragment : Fragment() {

    // 내정보
    lateinit var btnMemberInfo : Button
    lateinit var btnChangeNick : Button
    lateinit var btnChangePw : Button
    lateinit var btnLogout : Button
    lateinit var btnDeleteInfo : Button

    // 기타
    lateinit var btnHelp : Button
    lateinit var btnAnnounce : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_my_page, container, false)

        // my info

        btnMemberInfo = view.findViewById(R.id.btnMemberInfo)
        btnChangeNick = view.findViewById(R.id.btnChangeNick)
        btnChangePw = view.findViewById(R.id.btnChangePw)
        btnLogout = view.findViewById(R.id.btnLogOut)
        btnDeleteInfo = view.findViewById(R.id.btnDeleteInfo)

        btnMemberInfo.setOnClickListener {
                val intent = Intent(getActivity(), MemberInfo::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)

        }

        btnChangeNick.setOnClickListener {
            val intent = Intent(getActivity(), ChangeNick::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)

        }

        btnChangePw.setOnClickListener {
            val intent = Intent(getActivity(), ChangePw::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)

        }

        // Dialog 띄우기
        btnLogout.setOnClickListener {
            val intent = Intent(getActivity(), MemberInfo::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)

        }

        btnDeleteInfo.setOnClickListener {
            val intent = Intent(getActivity(), MemberInfo::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)

        }

        // else
        btnHelp = view.findViewById(R.id.btnHelp)
        btnAnnounce = view.findViewById(R.id.btnAnnounce)


        btnHelp.setOnClickListener {
            val intent = Intent(getActivity(), Help::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        btnAnnounce.setOnClickListener {
            val intent = Intent(getActivity(), Announce::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }


        return view

    }

}