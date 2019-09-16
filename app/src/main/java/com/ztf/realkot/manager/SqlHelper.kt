package com.ztf.realkot.manager

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ztf.realkot.bean.User

/**
 * @author ztf
 * @date 2019/9/5
 */
class SqlHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createSql = "create table student(id integer primary key autoincrement,name varchar(10),age integer)"
        db?.execSQL(createSql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @SuppressLint("Recycle")
    fun insertUser(user: User):MutableList<User> {
        val us = mutableListOf<User>()
        val values = ContentValues()
        values.put("id", user.id)
        values.put("name", user.userName)
        writableDatabase.insert("student", null, values)

        writableDatabase.delete("student", "id=?", arrayOf("1"))
        val cursor = writableDatabase.query("student", null, "id=?", arrayOf("1", "2"), null, null, "id")
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val u=User()
            u.userName = name
            us.add(u)
        }
        return us

    }
}