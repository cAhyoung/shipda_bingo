package com.example.happypig

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.happypig.databinding.FragmentHomeBinding
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    lateinit var imgViewProfile : ImageView
    lateinit var txtViewNick : TextView
    lateinit var txtViewRnd : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // 랜덤으로 문구 가져오는 코드

        binding = FragmentHomeBinding.inflate(layoutInflater)
        txtViewRnd = binding.txtViewRnd

        var txtRes = resources.getStringArray(R.array.mainRandomTxt)
        var rnd = Random()

        var n = rnd.nextInt(txtRes.size - 1)

        txtViewRnd?.text = txtRes[n].toString()

        // 배너광고



        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}