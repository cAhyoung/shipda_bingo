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

    lateinit var binding : FragmentMyPageBinding

    // 내정보
    lateinit var btnMemberInfo : Button
    lateinit var btnChangeNick : Button
    lateinit var btnChangePw : Button
    lateinit var btnLogout : Button
    lateinit var btnDeleteInfo : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(layoutInflater)

        btnMemberInfo = binding.btnMemberInfo
        btnChangeNick = binding.btnChangeNick
        btnChangePw = binding.btnChangePw
        btnLogout = binding.btnLogOut
        btnDeleteInfo = binding.btnDeleteInfo

        btnMemberInfo.setOnClickListener {
            val intent = Intent(getActivity(), MemberInfo::class.java)
            startActivity(intent)


        }


        return inflater.inflate(R.layout.fragment_my_page, container, false)

    }

}