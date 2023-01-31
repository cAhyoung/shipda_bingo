package com.example.happypig.tutorial

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.happypig.R
import com.example.happypig.login.LoginActivity

class homeTutorialFragment2 : Fragment() {

    lateinit var tutorial_start : Button


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home_tutorial2, container, false)

        val tActivity = activity as tutorialActivity

        tutorial_start = view.findViewById(R.id.tutorial_start)
        tutorial_start.setOnClickListener {
            var intent = Intent(tActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}