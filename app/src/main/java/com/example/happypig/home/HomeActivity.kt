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
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.happypig.DBManager
import com.example.happypig.R
import com.example.happypig.challenge.ChallengeFragment
import com.example.happypig.challenge.lv1BingoFragment
import com.example.happypig.challenge.lv2BingoFragment
import com.example.happypig.challenge.lv3BingoFragment
import com.example.happypig.mypage.MyPageFragment
import com.example.happypig.settingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class HomeActivity : AppCompatActivity(), SensorEventListener {

    //================homeActivity================
    lateinit var btmNav : BottomNavigationView

    val homeFragment by lazy { HomeFragment() }  // by lazy : 지연 초기화, 최초 사용 시 초기화
    val challengeFragment by lazy { ChallengeFragment() }
    val myPageFragment by lazy { MyPageFragment() }

    //================homeActivity2================
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
    lateinit var myPage : Fragment

    lateinit var container1 : FrameLayout

    private val id by lazy {
        intent.getStringExtra("id") as String
    }

    private val dateChangeFlag by lazy {
        intent.getBooleanExtra("dateChange", false) as Boolean
    }


    //====================================================================oncreate
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //loadFragment(homeFragment)

        id
        var lv = 0

        dbManager = DBManager(this, "guruDB", null, 1)

        fragmentManager = supportFragmentManager
        home = HomeFragment()
        lv1 = lv1BingoFragment()
        lv2 = lv2BingoFragment()
        lv3 = lv3BingoFragment()
        myPage = MyPageFragment()

        val bundle = Bundle()
        bundle.putString("id", id)

        home.arguments = bundle
        lv1.arguments = bundle
        lv2.arguments = bundle
        lv3.arguments = bundle
        myPage.arguments = bundle

        container1 = findViewById(R.id.fragmentContainer)

        //처음으로 커밋되는 프래그먼트... 레벨에 따라ㅁㄴ
        sqlitedb = dbManager.readableDatabase
        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT lv FROM personnel WHERE id = '" + id + "';", null)
        if(cursor.moveToNext()){
            lv = cursor.getInt(cursor.getColumnIndex("lv"))
        }
        else lv = 0

        cursor.close()
        sqlitedb.close()
        
        if(lv <= 1) fragmentManager.beginTransaction().replace(R.id.fragmentContainer, lv1).commit()
        if(lv == 2) fragmentManager.beginTransaction().replace(R.id.fragmentContainer, lv2).commit()
        if(lv == 3) fragmentManager.beginTransaction().replace(R.id.fragmentContainer, lv3).commit()

        btmNav = findViewById(R.id.btmNavView) as BottomNavigationView


        //하단 메뉴 선택 -> 프래그먼트 전환
        btmNav.setOnItemSelectedListener {
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
                R.id.home -> {
                    changeFragment(1)
                    true
                }
                R.id.bingo -> {
                    if (lv <= 1) changeFragment(2)
                    if (lv == 2) changeFragment(3)
                    if (lv >=3 ) changeFragment(4)
                    true
                }
                R.id.mypage -> {
                    changeFragment(5)
                    true
                }
                else -> {
                    true
                }
            }

        }

        /*
        btmNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(homeFragment)
                    true
                }
                R.id.bingo -> {
                    loadFragment(challengeFragment)
                    true
                }
                R.id.mypage -> {
                    loadFragment(myPageFragment)
                    true
                }
                else -> {
                    true
                }
            }
        }

         */


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
    /*
    // fragment 불러오는 함수
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

     */

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
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, home).commit()
                layoutForManbogi.visibility = View.VISIBLE
            }
            2-> {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, lv1).commit()
                layoutForManbogi.visibility = View.GONE
            }
            3-> {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, lv2).commit()
                layoutForManbogi.visibility = View.GONE
            }
            4 -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, lv3).commit()
                layoutForManbogi.visibility = View.GONE
            }

            5 -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, myPage).commit()
                layoutForManbogi.visibility = View.GONE
            }
        }
    }


}

