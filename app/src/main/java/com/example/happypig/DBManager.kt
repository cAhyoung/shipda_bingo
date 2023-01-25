package com.example.happypig

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBManager(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL("CREATE TABLE personnel (id text PRIMARY KEY, pw text, nickname text, email text, lv INTEGER, manbogi INTEGER, year INTEGER, month INTEGER, date INTEGER)")
        p0!!.execSQL("CREATE TABLE bingo1 (id text, tv0 INTEGER, tv1 INTEGER, tv2 INTEGER, tv3 INTEGER, tv4 INTEGER, tv5 INTEGER, tv6 INTEGER, tv7 INTEGER, tv8 INTEGER, flag0 INTEGER, flag1 INTEGER, flag2 INTEGER, flag3 INTEGER, flag4 INTEGER, flag5 INTEGER, flag6 INTEGER, flag7 INTEGER, flag8 INTEGER)")
        p0!!.execSQL("CREATE TABLE bingo2 (id text, tv0 INTEGER, tv1 INTEGER, tv2 INTEGER, tv3 INTEGER, tv4 INTEGER, tv5 INTEGER, tv6 INTEGER, tv7 INTEGER, tv8 INTEGER, flag0 INTEGER, flag1 INTEGER, flag2 INTEGER, flag3 INTEGER, flag4 INTEGER, flag5 INTEGER, flag6 INTEGER, flag7 INTEGER, flag8 INTEGER)")
        p0!!.execSQL("CREATE TABLE bingo3 (id text, tv0 INTEGER, tv1 INTEGER, tv2 INTEGER, tv3 INTEGER, tv4 INTEGER, tv5 INTEGER, tv6 INTEGER, tv7 INTEGER, tv8 INTEGER, flag0 INTEGER, flag1 INTEGER, flag2 INTEGER, flag3 INTEGER, flag4 INTEGER, flag5 INTEGER, flag6 INTEGER, flag7 INTEGER, flag8 INTEGER)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}