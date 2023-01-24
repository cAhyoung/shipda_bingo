package com.example.happypig.home

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.happypig.MainActivity
import com.example.happypig.R
import com.example.happypig.databinding.FragmentHomeBinding
import java.util.*



class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    lateinit var imgViewProfile : ImageView
    lateinit var txtViewNick : TextView
    lateinit var txtViewRnd : TextView

    lateinit var btnTrash : ImageButton
    lateinit var btnGlass: ImageButton
    lateinit var btnPaper: ImageButton
    lateinit var btnPlastic : ImageButton
    lateinit var btnVinyl : ImageButton
    lateinit var btnCan : ImageButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val mActivity = activity as HomeActivity
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // 랜덤으로 문구 가져오는 코드

        binding = FragmentHomeBinding.inflate(layoutInflater)
        txtViewRnd = binding.txtViewRnd

        var txtRes = resources.getStringArray(R.array.mainRandomTxt)
        var rnd = Random()

        var n = rnd.nextInt(txtRes.size - 1)

        txtViewRnd?.text = txtRes[n].toString()

        // 배너광고 -> 잠깐 좀 미룰게요


        // 버튼 클릭 시 분리수거 방법

        btnTrash = binding.btnTrash
        btnGlass = binding.btnGlass
        btnPaper = binding.btnPaper
        btnPlastic = binding.btnPlastic
        btnVinyl = binding.btnVinyl
        btnCan = binding.btnCan

        btnTrash.setOnClickListener {
            showDialog(mActivity, 1)
        }

        btnGlass.setOnClickListener {
            showDialog(mActivity, 2)
        }

        btnPaper.setOnClickListener {
            showDialog(mActivity, 3)
        }

        btnPlastic.setOnClickListener {
            showDialog(mActivity, 4)
        }

        btnVinyl.setOnClickListener {
            showDialog(mActivity, 5)
        }

        btnCan.setOnClickListener {
            showDialog(mActivity, 6)
        }


        return inflater.inflate(R.layout.fragment_home, container, false)
    }



    private fun showDialog(context: Context, btn: Int) {

        var dialogView = View.inflate(context, R.layout.dialog, null)

        val trash = dialogView.findViewById<ImageView>(R.id.btnTrash)
        val glass = dialogView.findViewById<ImageView>(R.id.btnGlass)
        val paper = dialogView.findViewById<ImageView>(R.id.btnPaper)
        val plastic = dialogView.findViewById<ImageView>(R.id.btnPlastic)
        val vinyl = dialogView.findViewById<ImageView>(R.id.btnVinyl)
        val can = dialogView.findViewById<ImageView>(R.id.btnCan)

        var dialog = AlertDialog.Builder(context)

        dialog.setView(dialogView)
        dialog.setPositiveButton("확인", null)

        if (btn == 1) {
            trash.visibility = View.VISIBLE
        }
        else if (btn == 2) {
            glass.visibility = View.VISIBLE
        }
        else if (btn == 3) {
            paper.visibility = View.VISIBLE
        }
        else if (btn == 4) {
            plastic.visibility = View.VISIBLE
        }
        else if (btn == 5) {
            vinyl.visibility = View.VISIBLE
        }
        else {
            can.visibility = View.VISIBLE
        }

    }



}



