package com.example.happypig

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity2 : AppCompatActivity() {

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



    var lv = 1

    @SuppressLint("MissingInflatedId", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)


        val id = intent.getStringExtra("id")
        var lv = 0

        dbManager = DBManager(this, "guruDB", null, 1)
        sqlitedb = dbManager.readableDatabase
        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT lv FROM personnel WHERE id = '" + id + "';", null)
        if(cursor.moveToNext()){
            lv = cursor.getInt(cursor.getColumnIndex("lv"))
        }
        else lv = 0



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


        /*
        fragmentManager.beginTransaction().replace(R.id.container2, lv1).commit()
        fragmentManager.beginTransaction().replace(R.id.container3, lv2).commit()
        fragmentManager.beginTransaction().replace(R.id.container4, lv3).commit()
        fragmentManager.beginTransaction().replace(R.id.container5, settings).commit()

         */

        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)


        bottom_navigation.setOnItemSelectedListener {
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





    }

/*
    fun changeFragment(index : Int) {
        when(index) {
            1-> {
                if (home == null )supportFragmentManager.beginTransaction().replace(R.id.container1, home).commit()

                if(home != null) container1.visibility = View.VISIBLE
                if(lv1 != null) container2.visibility = View.GONE
                if(lv2 != null) container3.visibility = View.GONE
                if(lv3 != null) container4.visibility = View.GONE
                if(settings != null) container5.visibility = View.GONE


            }
            2-> {
                if (lv == null) supportFragmentManager.beginTransaction().replace(R.id.container2, lv1).commit()

                if(home != null) container1.visibility = View.GONE
                if(lv1 != null) container2.visibility = View.VISIBLE
                if(lv2 != null) container3.visibility = View.GONE
                if(lv3 != null) container4.visibility = View.GONE
                if(settings != null) container5.visibility = View.GONE

            }
            3-> {
                if(lv2 == null )supportFragmentManager.beginTransaction().replace(R.id.container3, lv2).commit()

                if(home != null) container1.visibility = View.GONE
                if(lv1 != null) container2.visibility = View.GONE
                if(lv2 != null) container3.visibility = View.VISIBLE
                if(lv3 != null) container4.visibility = View.GONE
                if(settings != null) container5.visibility = View.GONE
            }
            4 -> {
                if (lv3 == null) supportFragmentManager.beginTransaction().replace(R.id.container4, lv3).commit()

                if(home != null) container1.visibility = View.GONE
                if(lv1 != null) container2.visibility = View.GONE
                if(lv2 != null) container3.visibility = View.GONE
                if(lv3 != null) container4.visibility = View.VISIBLE
                if(settings != null) container5.visibility = View.GONE
            }

            5 -> {
                if (settings == null) supportFragmentManager.beginTransaction().replace(R.id.container4, settings).commit()

                if(home != null) container1.visibility = View.GONE
                if(lv1 != null) container2.visibility = View.GONE
                if(lv2 != null) container3.visibility = View.GONE
                if(lv3 != null) container4.visibility = View.GONE
                if(settings != null) container5.visibility = View.VISIBLE
            }
        }
    }

 */
fun changeFragment(index : Int) {
    when(index) {
        1-> {
            supportFragmentManager.beginTransaction().replace(R.id.container1, home).commit()

        }
        2-> {
            supportFragmentManager.beginTransaction().replace(R.id.container1, lv1).commit()

        }
        3-> {
            supportFragmentManager.beginTransaction().replace(R.id.container1, lv2).commit()
        }
        4 -> {
            supportFragmentManager.beginTransaction().replace(R.id.container1, lv3).commit()
        }

        5 -> {
            supportFragmentManager.beginTransaction().replace(R.id.container1, settings).commit()
        }
    }
}


}
