package com.example.happypig

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [lv1BingoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class lv1BingoFragment : Fragment() {


    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var iv1_c: ImageView
    lateinit var iv1_b: ImageView
    lateinit var iv2_c: ImageView
    lateinit var iv2_b: ImageView
    lateinit var iv3_c: ImageView
    lateinit var iv3_b: ImageView

    lateinit var back : Button
    lateinit var next : Button



    // TODO: Rename and change types of parameters
    private var id: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString("id")
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("Range", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lv1_bingo, container, false)


        dbManager = DBManager(context, "guruDB", null, 1)

        iv1_c = view.findViewById(R.id.iv1_c)
        iv1_b = view.findViewById(R.id.iv1_b)
        iv2_c = view.findViewById(R.id.iv2_c)
        iv2_b = view.findViewById(R.id.iv2_b)
        iv3_c = view.findViewById(R.id.iv3_c)
        iv3_b = view.findViewById(R.id.iv3_b)

        back = view.findViewById(R.id.back)
        next = view.findViewById(R.id.next)


        var checked = Array<Boolean>(9) { false }


        val tvId = arrayOf(
            R.id.tv1,
            R.id.tv2,
            R.id.tv3,
            R.id.tv4,
            R.id.tv5,
            R.id.tv6,
            R.id.tv7,
            R.id.tv8,
            R.id.tv9
        )

        val ivId = arrayOf(
            R.id.check1,
            R.id.check2,
            R.id.check3,
            R.id.check4,
            R.id.check5,
            R.id.check6,
            R.id.check7,
            R.id.check8,
            R.id.check9
        )

        var tv = Array<TextView>(9) { view.findViewById(R.id.tv1) }
        var checks = Array<ImageView>(9) {view.findViewById(R.id.check1)}
        var bingo = view.findViewById<TextView>(R.id.bingo)
        var bingoNum: Int


        var randChal = resources.getStringArray(R.array.level1)

        var level = 1
        var userlevel = 0
        sqlitedb = dbManager.readableDatabase
        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT lv FROM personnel WHERE id = '" + id + "';",null)
        if (cursor.moveToNext()){
            userlevel = cursor.getInt(cursor.getColumnIndex("lv"))
        }

        cursor.close()
        sqlitedb.close()

        var levelupFlag = false

        val homeActivity = activity as HomeActivity2

        if (userlevel > level) {
            levelupFlag = true
            next.isEnabled = true
        }

        //위젯과 id 연결
        for (i in 0..8) {
            val index: Int = i
            tv[index] = view.findViewById(tvId[index])
            checks[index] = view.findViewById(ivId[index])
        }



        if (userlevel == 0) {
            //앱 최초 실행
            //빙고 랜덤하게 배치
            userlevel = 1
            val random = Random()

            //DB
            sqlitedb = dbManager.writableDatabase
            sqlitedb.execSQL("INSERT INTO bingo1 VALUES ('" + id + "', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 );")
            sqlitedb.close()


            //난수 생성 후 string 배열에 접근
            val list = mutableListOf<Int>()
            var index = 0
            while (list.size < 9) {
                var row : String = "tv" + index
                val randomnum = random.nextInt(17)
                if (list.contains(randomnum)) {
                    continue
                }
                list.add(randomnum)
                tv[index].text = randChal[randomnum]

                //db
                sqlitedb = dbManager.writableDatabase
                sqlitedb.execSQL("UPDATE bingo1 SET " + row + " = " + randomnum + " WHERE id = '" + id + "';")

                index++

            }

        } else {
            //앱을 실행한 적이 있으면
            //디비에서 기존 플레이 이력을 가져옴
            cursor.close()

            for ( i in 0..8){
                var row : String = "tv" + i
                var index : Int = 0
                sqlitedb = dbManager.readableDatabase
                cursor = sqlitedb.rawQuery("SELECT "+ row + " FROM bingo1 WHERE id = '" + id + "';", null)
                if (cursor.moveToNext()) index = cursor.getInt(cursor.getColumnIndex(row))
                tv[i].text = randChal[index].toString()
                cursor.close()

                row = "flag" + i
                cursor = sqlitedb.rawQuery("SELECT "+ row + " FROM bingo1 WHERE id = '" + id + "';", null)
                if (cursor.moveToNext()){
                    var flag = cursor.getInt(cursor.getColumnIndex(row))
                    if (flag == 0) checked[i] = false
                    else checked[i] = true
                }
                cursor.close()

                if(checked[i]) checks[i].visibility = View.VISIBLE
                else checks[i].visibility = View.INVISIBLE

                bingoNum = bingoDetector(tv, checked)
                bingo.text = bingoNum.toString() + " 빙고!"

                if (bingoNum >=2) {
                    iv1_b.visibility = View.INVISIBLE
                    iv1_c.visibility = View.VISIBLE
                }
                else {
                    iv1_b.visibility = View.VISIBLE
                    iv1_c.visibility = View.INVISIBLE
                }
                if (bingoNum >= 4) {
                    iv2_b.visibility = View.INVISIBLE
                    iv2_c.visibility = View.VISIBLE
                }
                else{

                    iv2_b.visibility = View.VISIBLE
                    iv2_c.visibility = View.INVISIBLE
                }
                if (bingoNum == 8) {
                    iv3_b.visibility = View.INVISIBLE
                    iv3_c.visibility = View.VISIBLE
                }
                else{
                    iv3_b.visibility = View.VISIBLE
                    iv3_c.visibility = View.INVISIBLE
                }


            }

        }

        sqlitedb.close()

        //빙고 게임 진행
        for ( i  in 0 .. 8) {
            val index: Int = i
            tv[index].setOnClickListener {
                clicked(tv[index], checks[index], checked, index)
                //db
                var row : String = "flag" + i
                var value : Int
                if (checked[index]) value = 1
                else value = 0
                sqlitedb = dbManager.writableDatabase
                sqlitedb.execSQL("UPDATE bingo1 set " + row  + " = " + value + " where id = '" + id + "';")
                sqlitedb.close()


                bingoNum = bingoDetector(tv, checked)
                bingo.text = "$bingoNum 빙고!"


                if (!levelupFlag){
                    userlevel = levelUp(bingoNum, level, view.context) //디비 업데이트하기
                    if (userlevel == 2){
                        levelupFlag = true
                        next.isEnabled = true

                        sqlitedb = dbManager.writableDatabase
                        sqlitedb.execSQL("UPDATE personnel SET lv = " + userlevel + " WHERE id = '" +id + "';")
                        sqlitedb.close()
                    }
                }

                if (bingoNum >=2) {
                    iv1_b.visibility = View.INVISIBLE
                    iv1_c.visibility = View.VISIBLE
                }
                else {
                    iv1_b.visibility = View.VISIBLE
                    iv1_c.visibility = View.INVISIBLE
                }
                if (bingoNum >= 4) {
                    iv2_b.visibility = View.INVISIBLE
                    iv2_c.visibility = View.VISIBLE
                }
                else{

                    iv2_b.visibility = View.VISIBLE
                    iv2_c.visibility = View.INVISIBLE
                }
                if (bingoNum == 8) {
                    iv3_b.visibility = View.INVISIBLE
                    iv3_c.visibility = View.VISIBLE
                }
                else{
                    iv3_b.visibility = View.VISIBLE
                    iv3_c.visibility = View.INVISIBLE
                }


            }
        }

        next.setOnClickListener {
            homeActivity.changeFragment(3)
        }

        //재배치 button
        var randomize = view.findViewById<Button>(R.id.btnRandmoize)
        randomize.setOnClickListener {
            reset(tv, checks, checked)
            bingo.text = "0"


            //랜덤하게 재배치
            val random = Random()

            val list = mutableListOf<Int>()
            var index = 0
            while (list.size < 9) {
                var row : String = "tv" + index
                val randomnum = random.nextInt(17)
                if (list.contains(randomnum)) {
                    continue
                }
                list.add(randomnum)
                tv[index].text = randChal[randomnum]

                sqlitedb = dbManager.writableDatabase
                sqlitedb.execSQL("UPDATE bingo1 set " +row + " = " + randomnum + " where id = '" + id + "';")
                sqlitedb.close()

                row = "flag" + index
                sqlitedb = dbManager.writableDatabase
                sqlitedb.execSQL("UPDATE bingo1 set " +row + " = 0 where id = '" + id + "';")

                index++

            }

            //이미지 뷰
            iv1_c.visibility = View.INVISIBLE
            iv1_b.visibility = View.VISIBLE
            iv2_c.visibility = View.INVISIBLE
            iv2_b.visibility = View.VISIBLE
            iv3_c.visibility = View.INVISIBLE
            iv3_b.visibility = View.VISIBLE

        }

        //리셋 버튼
        var reset = view.findViewById<Button>(R.id.btnReset)
        reset.setOnClickListener {
            reset(tv, checks, checked)
            bingo.text = "0"

            for (i in 0..8){
                var row = "flag" + i
                sqlitedb = dbManager.writableDatabase
                sqlitedb.execSQL("UPDATE bingo1 set " +row + " = 0 where id = '" + id + "';")
            }
            //이미지 뷰
            iv1_c.visibility = View.INVISIBLE
            iv1_b.visibility = View.VISIBLE
            iv2_c.visibility = View.INVISIBLE
            iv2_b.visibility = View.VISIBLE
            iv3_c.visibility = View.INVISIBLE
            iv3_b.visibility = View.VISIBLE
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment lv1BingoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            lv1BingoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("Range")
    private fun clicked(tv : TextView, iv : ImageView, flag : Array<Boolean>, num : Int) {

        when (flag[num]) {
            true -> {
                flag[num] = false
            }
            false -> {
                flag[num] = true
            }
        }

        if(flag[num]){
            //빙고로 체크됨
            iv.visibility = View.VISIBLE
        }
        else {
            //빙고 취소
            iv.visibility = View.INVISIBLE
        }

        if (flag[num]) {

            val row = "tv"+num

            dbManager = DBManager(context, "guruDB", null, 1)
            sqlitedb = dbManager.readableDatabase
            var cursor : Cursor
            cursor = sqlitedb.rawQuery("SELECT " + row + " FROM bingo1 WHERE id = '" + id + "';", null)
            if (cursor.moveToNext()){
                val web = cursor.getInt(cursor.getColumnIndex(row))
                when (web) {
                    0 -> {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bbc.com/korean/international-61864499"))
                        startActivity(intent)
                    }
                    1 -> {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://news.skecoplant.com/plant-tomorrow/5352/"))
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://thepicker.net/JOURNEYTOZERO/?idx=3162382&bmode=view"))
                        startActivity(intent)
                    }
                }
            }
            cursor.close()
            sqlitedb.close()
            dbManager.close()
        }


    }

    private fun reset(tv : Array<TextView>, iv : Array<ImageView>, flag : Array<Boolean>) {
        for (i in 0..8){
            val index : Int = i
            //tv[index].setTextColor(Color.BLACK)
            iv[index].visibility = View.INVISIBLE
            flag[index] = false
        }
    }

    private fun bingoDetector (tv : Array<TextView>, flag : Array<Boolean>) : Int {
        var arr2d = Array<BooleanArray>(3,{BooleanArray(3)})
        var index = 0
        //var bingoArray = Array<Boolean>(8) {false}

        var bingo = 0

        for(i in 0..2){
            for (j in 0..2) {
                arr2d[i][j] = flag[index]
                index++
            }
        }

        for ( i in 0..2){
            if(arr2d[i][0] && arr2d[i][1] && arr2d[i][2]) bingo++
            if(arr2d[0][i] && arr2d[1][i] && arr2d[2][i]) bingo++
        }

        if (arr2d[0][0] && arr2d[1][1] && arr2d[2][2]) bingo++
        if (arr2d[0][2] && arr2d[1][1] && arr2d[2][0]) bingo++


        return bingo
    }

    private fun levelUp(bingoNum : Int, lv : Int, context: Context) : Int{
        var level = lv
        if (bingoNum >= 4) {
            level++

            val dlg = AlertDialog.Builder(context)
            dlg.setTitle("Level Up!")
            dlg.setMessage("$level 레벨이 되셨습니다.\nnext 버튼을 눌러서 다음 레벨을 플레이할 수 있고,\n리셋 버튼을 눌러 이전 레벨을 다시 플레이 할 수 있습니다.")
            dlg.setPositiveButton("확인",null)
            dlg.show()

        }

        return level
    }
}