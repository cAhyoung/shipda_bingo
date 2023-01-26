package com.example.happypig.home

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.viewpager2.widget.ViewPager2
import com.example.happypig.R
import com.example.happypig.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    lateinit var btnTrash : ImageButton
    lateinit var btnGlass: ImageButton
    lateinit var btnPaper: ImageButton
    lateinit var btnPlastic : ImageButton
    lateinit var btnVinyl : ImageButton
    lateinit var btnCan : ImageButton

    lateinit var viewPagerAd : ViewPager2
    var currentPosition = 0
    val handler = Handler(Looper.getMainLooper()) {
        setPage()
        true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val hActivity = activity as HomeActivity



        // 랜덤으로 문구 가져오는 코드
        // 만보기로 대체


        // 배너광고
        // 다른 fragment를 갔다가 home으로 돌아오는 경우 슬라이드 배너가 좀더 빠르게 혹은 랜덤으로 넘어가버림
        viewPagerAd = view.findViewById(R.id.adView)
        viewPagerAd.adapter = ViewpagerAdapter(getAdBanners())
        viewPagerAd.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        var thread = Thread(PagerRunnable())
        thread.start()



        // 버튼 클릭 시 분리수거 방법

        btnTrash = view.findViewById(R.id.btnTrash)
        btnGlass = view.findViewById(R.id.btnGlass)
        btnPaper = view.findViewById(R.id.btnPaper)
        btnPlastic = view.findViewById(R.id.btnPlastic)
        btnVinyl = view.findViewById(R.id.btnVinyl)
        btnCan = view.findViewById(R.id.btnCan)

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

    private fun getAdBanners() : ArrayList<Int> {
        return arrayListOf<Int> (
            R.drawable.ad_banner1,
            R.drawable.ad_banner2
                )
    }

    fun setPage() {
        if(currentPosition == 2) currentPosition = 0
        viewPagerAd.setCurrentItem(currentPosition, true)
        currentPosition += 1
    }

    inner class PagerRunnable : Runnable {
        override fun run() {
            while (true) {
                try {
                    Thread.sleep(3000)
                    handler.sendEmptyMessage(0)
                } catch (e: InterruptedException) {
                    Log.d("interupt", "interupt발생")
                }
            }
        }
    }



}

