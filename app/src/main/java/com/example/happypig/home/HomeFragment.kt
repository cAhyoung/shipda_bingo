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

        val hActivity = activity as HomeActivity



        // 랜덤으로 문구 가져오는 코드
        // 만보기로 대체


        // 배너광고 -> 잠깐 좀 미룰게요


        // 버튼 클릭 시 분리수거 방법

        btnTrash = view.findViewById(R.id.btnTrash)
        btnGlass = view.findViewById(R.id.btnGlass)
        btnPaper = view.findViewById(R.id.btnPaper)
        btnPlastic = view.findViewById(R.id.btnPlastic)
        btnVinyl = view.findViewById(R.id.btnVinyl)
        btnCan = view.findViewById(R.id.btnCan)


        btnTrash = view.findViewById(R.id.btnTrash)
        var dialogView = View.inflate(hActivity, R.layout.dlgimg, null)
        btnTrash.setOnClickListener {
            var dialogView = View.inflate(hActivity, R.layout.dialog_trash, null)
            var dlg = AlertDialog.Builder(hActivity)
            dlg.setTitle("일반쓰레기 배출 방법")
            dlg.setView(dialogView)
            dlg.setCancelable(true)
            dlg.setPositiveButton("확인", null)
            dlg.show()
        }

        btnGlass.setOnClickListener {
            var dialogView = View.inflate(hActivity, R.layout.dialog_glass, null)
            var dlg = AlertDialog.Builder(hActivity)
            dlg.setTitle("유리 배출 방법")
            dlg.setView(dialogView)
            dlg.setCancelable(true)
            dlg.setPositiveButton("확인", null)
            dlg.show()

        }

        btnPaper.setOnClickListener {
            var dialogView = View.inflate(hActivity, R.layout.dialog_paper, null)
            var dlg = AlertDialog.Builder(hActivity)
            dlg.setTitle("종이 배출 방법")
            dlg.setView(dialogView)
            dlg.setCancelable(true)
            dlg.setPositiveButton("확인", null)
            dlg.show()

        }

        btnPlastic.setOnClickListener {
            var dialogView = View.inflate(hActivity, R.layout.dialog_plastic, null)
            var dlg = AlertDialog.Builder(hActivity)
            dlg.setTitle("플라스틱 배출 방법")
            dlg.setView(dialogView)
            dlg.setCancelable(true)
            dlg.setPositiveButton("확인", null)
            dlg.show()
        }

        btnVinyl.setOnClickListener {
            var dialogView = View.inflate(hActivity, R.layout.dialog_vinyl, null)
            var dlg = AlertDialog.Builder(hActivity)
            dlg.setTitle("비닐 배출 방법")
            dlg.setView(dialogView)
            dlg.setCancelable(true)
            dlg.setPositiveButton("확인", null)
            dlg.show()
        }

        btnCan.setOnClickListener {
            var dialogView = View.inflate(hActivity, R.layout.dialog_can, null)
            var dlg = AlertDialog.Builder(hActivity)
            dlg.setTitle("캔 배출 방법")
            dlg.setView(dialogView)
            dlg.setCancelable(true)
            dlg.setPositiveButton("확인", null)
            dlg.show()
        }


        return view
    }


 }



