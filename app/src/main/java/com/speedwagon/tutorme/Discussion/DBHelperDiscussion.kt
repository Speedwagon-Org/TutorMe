package com.speedwagon.tutorme.Discussion

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.firebase.auth.FirebaseAuth

class DBHelperDiscussion(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    private  lateinit var auth: FirebaseAuth

    override fun onCreate(db: SQLiteDatabase?) {
        auth = FirebaseAuth.getInstance()
        val CREATE_USER_TABLE = ("CREATE TABLE " +
                TABLE_NAME+ "(" +
                ID_USER+ " INTEGER PRIMARY KEY," +
                USERNAME+ " TEXT," +
                TEXT + " TEXT" + ")")
        db?.execSQL(CREATE_USER_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " +
                TABLE_NAME)
        onCreate(db)
    }
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "mysqlitedbex.db"
        val TABLE_NAME = "Comment"
        val ID_USER = "_id"
        val USERNAME = "Name"
        val TEXT = "Text"
    }
}