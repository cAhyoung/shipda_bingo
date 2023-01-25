package com.example.happypig.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.happypig.*
import com.example.happypig.challenge.lv1BingoFragment
import com.example.happypig.challenge.lv2BingoFragment
import com.example.happypig.challenge.lv3BingoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class HomeActivity2 : AppCompatActivity(), SensorEventListener {

    //만보기를 위한 변수
    //센서 세팅
    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    private val stepCountSensor by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
    }

    lateinit var tvmanbogiNum: TextView
    lateinit var manbogiReset: Button
    //lateinit var tvPoint : TextView
    lateinit var layoutForManbogi : LinearLayout

    var currentSteps = 0
    //var points = 0
    //val startDate = Date()

    var currentYear = 0
    var currentMonth= 0
    var currentDate = 0


    //빙고를 위한 변수
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var fragmentManager : FragmentManager
    lateinit var home : Fragment
    lateinit var lv1 : Fragment
    lateinit var lv2 : Fragment
    lateinit var lv3 : Fragment
    lateinit var settings : Fragment

    lateinit var container1 : FrameLayout
    lateinit var container2 : FrameLayout
    lateinit var container3 : FrameLayout
    lateinit var container4 : FrameLayout
    lateinit var container5 : FrameLayout

    private val id by lazy {
        intent.getStringExtra("id") as String
    }

    private val dateChangeFlag by lazy {
        intent.getBooleanExtra("dateChange", false) as Boolean
    }

    @SuppressLint("MissingInflatedId", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)
        //val id = intent.getStringExtra("id")
        id
        var lv = 0

        dbManager = DBManager(this, "guruDB", null, 1)




        fragmentManager = supportFragmentManager
        home = HomeFragment2()
        lv1 = lv1BingoFragment()
        lv2 = lv2BingoFragment()
        lv3 = lv3BingoFragment()
        settings = settingsFragment()

        val bundle = Bundle()
        bundle.putString("id", id)

        home.arguments = bundle
        lv1.arguments = bundle
        lv2.arguments = bundle
        lv3.arguments = bundle
        settings.arguments = bundle

        container1 = findViewById(R.id.container1)
        container2 = findViewById(R.id.container2)
        container3 = findViewById(R.id.container3)
        container4 = findViewById(R.id.container4)
        container5 = findViewById(R.id.container5)


        fragmentManager.beginTransaction().replace(R.id.container1, home).commit()


        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)


        bottom_navigation.setOnItemSelectedListener {
            sqlitedb = dbManager.readableDatabase
            var cursor : Cursor
            cursor = sqlitedb.rawQuery("SELECT lv FROM personnel WHERE id = '" + id + "';", null)
            if(cursor.moveToNext()){
                lv = cursor.getInt(cursor.getColumnIndex("lv"))
            }
            else lv = 0

            cursor.close()
            sqlitedb.close()


            when(it.itemId) {
                R.id.tab_home -> {
                    changeFragment(1)
                    true
                }
                R.id.tab_bingo -> {
                    if (lv <= 1) changeFragment(2)
                    if (lv == 2) changeFragment(3)
                    if (lv >=3 ) changeFragment(4)
                    true
                }
                R.id.tab_setting -> {
                    changeFragment(5)
                    true
                }
                else -> {
                    true
                }
            }
        }


        //만보기
        tvmanbogiNum = findViewById(R.id.tvmanbogiNum)
        manbogiReset = findViewById(R.id.manbogiReset)
        layoutForManbogi = findViewById(R.id.layoutForManbogi)

        //db에서 걸음수 가져오기
        if (dateChangeFlag){
            //날짜가 바뀌었다면 currentsteps = 0으로 초기화
            currentSteps = 0
            tvmanbogiNum.text = currentSteps.toString()
        }
        else {
            sqlitedb = dbManager.readableDatabase
            var cursor : Cursor
            cursor = sqlitedb.rawQuery("SELECT manbogi FROM personnel WHERE id = '" + id + "'",null)
            if (cursor.moveToNext()){
                currentSteps = cursor.getInt(cursor.getColumnIndex("manbogi"))
                tvmanbogiNum.text = currentSteps.toString()
            }
            cursor.close()
            sqlitedb.close()
        }


        // 활동 퍼미션 체크
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACTIVITY_RECOGNITION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            //권한이 혀용되지 않음
            //최소 sdk가 23 이상이어야 함
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),
                1000
            )
        } else {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        sensorManager
        stepCountSensor


        //디바이스에 걸음 센서 존재 여부 체크
        if (stepCountSensor == null) Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT)
            .show();


        //리셋버튼
        manbogiReset.setOnClickListener {
            currentSteps = 0
            tvmanbogiNum.text = "$currentSteps"
            //points = 0
            //tvPoint.text = "포인트 : $points"
            sqlitedb = dbManager.writableDatabase
            sqlitedb.execSQL("UPDATE personnel SET manbogi = " + currentSteps + " WHERE id = '" + id + "';")
            sqlitedb.close()
        }



    }

    override fun onStart() {
        super.onStart()

        if (stepCountSensor != null) {
            // 센서 속도 설정
            // * 옵션
            // - SENSOR_DELAY_NORMAL: 20,000 초 딜레이
            // - SENSOR_DELAY_UI: 6,000 초 딜레이
            // - SENSOR_DELAY_GAME: 20,000 초 딜레이
            // - SENSOR_DELAY_FASTEST: 딜레이 없음
            //
            sensorManager.registerListener(
                this,
                stepCountSensor,
                SensorManager.SENSOR_DELAY_FASTEST
            );
        }
    }

    @SuppressLint("Range")
    override fun onSensorChanged(event: SensorEvent?) {
        // 걸음 센서 이벤트 발생시
        if (event != null) {
            if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                if (event.values[0] == 1.0f) {
                    // 센서 이벤트가 발생할때 마다 걸음수 증가

                    if(currentSteps >= 1000) currentSteps = 10000
                    else currentSteps++;

                    tvmanbogiNum.text = "$currentSteps"

                    sqlitedb = dbManager.writableDatabase
                    sqlitedb.execSQL("UPDATE personnel SET manbogi = " + currentSteps + " WHERE id = '" + id + "';")
                    sqlitedb.close()

                    //현재 시간 구하기
                    currentYear = Date().year
                    currentMonth = Date().month
                    currentDate = Date().date

                }

                //현재 날짜와 실행 날짜를 비교해서 현재 날짜가 더 크다면(날이 바뀌었다면) 만보기 초기화
                sqlitedb = dbManager.readableDatabase
                var cursor : Cursor
                cursor = sqlitedb.rawQuery("SELECT year, month, date FROM personnel WHERE id = '" + id + "';", null)
                var startYear = 0
                var startMonth = 0
                var startDate = 0
                if(cursor.moveToNext()){
                    startYear = cursor.getInt(cursor.getColumnIndex("year"))
                    startMonth = cursor.getInt(cursor.getColumnIndex("month"))
                    startDate = cursor.getInt(cursor.getColumnIndex("date"))

                }
                cursor.close()
                sqlitedb.close()

                if (currentDate > startDate) {
                    currentSteps = 0
                    tvmanbogiNum.text = "$currentSteps"
                }
                else if (currentMonth > startMonth) {
                    currentSteps = 0
                    tvmanbogiNum.text = "$currentSteps"
                }
                else if (currentYear > startYear) {
                    currentSteps = 0
                    tvmanbogiNum.text = "$currentSteps"
                }

                sqlitedb = dbManager.writableDatabase
                sqlitedb.execSQL("UPDATE personnel SET year = " + currentYear + ", month = " + currentMonth + ", date = " + currentDate + " WHERE id = '" + id + "';")
                sqlitedb.close()


            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {
        super.onPointerCaptureChanged(hasCapture)
    }
    //프래그먼트 전환
    fun changeFragment(index : Int) {
        when(index) {
            1-> {
                supportFragmentManager.beginTransaction().replace(R.id.container1, home).commit()
                layoutForManbogi.visibility = View.VISIBLE
            }
            2-> {
                supportFragmentManager.beginTransaction().replace(R.id.container1, lv1).commit()
                layoutForManbogi.visibility = View.GONE
            }
            3-> {
                supportFragmentManager.beginTransaction().replace(R.id.container1, lv2).commit()
                layoutForManbogi.visibility = View.GONE
            }
            4 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container1, lv3).commit()
                layoutForManbogi.visibility = View.GONE
            }

            5 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container1, settings).commit()
                layoutForManbogi.visibility = View.GONE
            }
        }
    }


}
