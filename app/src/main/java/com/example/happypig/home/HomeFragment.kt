package com.example.happypig.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.example.happypig.R
import com.example.happypig.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var id: String? = null

    private lateinit var binding: FragmentHomeBinding

    lateinit var btnTrash: ImageButton
    lateinit var btnGlass: ImageButton
    lateinit var btnPaper: ImageButton
    lateinit var btnPlastic: ImageButton
    lateinit var btnVinyl: ImageButton
    lateinit var btnCan: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString("id")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val homeActivity = activity as HomeActivity  //mainActivity에서 homeActivity로 변수명 수정

        binding = FragmentHomeBinding.inflate(layoutInflater)

        // 랜덤으로 문구 가져오는 코드
        // 만보기로 대체


        // 배너광고 -> 잠깐 좀 미룰게요


        // 버튼 클릭 시 분리수거 방법

//        btnTrash = binding.btnTrash
//        btnGlass = binding.btnGlass
//        btnPaper = binding.btnPaper
//        btnPlastic = binding.btnPlastic
//        btnVinyl = binding.btnVinyl
//        btnCan = binding.btnCan
//
//        btnTrash.setOnClickListener {
//            showDialog(this, 1)
//        }
//
//        btnGlass.setOnClickListener {
//            showDialog(this, 2)
//        }
//
//        btnPaper.setOnClickListener {
//            showDialog(this, 3)
//        }
//
//        btnPlastic.setOnClickListener {
//            showDialog(this, 4)
//        }
//
//        btnVinyl.setOnClickListener {
//            showDialog(this, 5)
//        }
//
//        btnCan.setOnClickListener {
//            showDialog(this, 6)
//        }




        return view

    }






}





