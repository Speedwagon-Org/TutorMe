package com.speedwagon.tutorme.Profile

import android.content.Context
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.firebase.auth.FirebaseAuth

class DBhelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    private  lateinit var auth: FirebaseAuth

    override fun onCreate(db: SQLiteDatabase?) {
        auth = FirebaseAuth.getInstance()
        val CREATE_USER_TABLE = ("CREATE TABLE " +
                TABLE_NAME+ "(" +
                ID_USER+ " TEXT PRIMARY KEY," +
                DES + " TEXT" + ")")
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
        val TABLE_NAME = "profiledata"
        val ID_USER = "id"
        val DES = "description"
    }

    fun SaveProfiledata(deskripsi : String) {
        auth = FirebaseAuth.getInstance()
        val values = ContentValues()
        values.put(DES, deskripsi)
        values.put(ID_USER, auth.uid.toString())

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun fetch(): Cursor {
        auth = FirebaseAuth.getInstance()
        val db = this.readableDatabase
        return db.rawQuery("SELECT ${DES} FROM " + TABLE_NAME + " WHERE id = '${auth.uid.toString()}'", null)
    }

    fun update(deskripsi : String) {
        auth = FirebaseAuth.getInstance()
        val values = ContentValues()
        values.put(DES, deskripsi)
        values.put(ID_USER, auth.uid.toString())

        val db = this.writableDatabase

        db.update(TABLE_NAME,  values , "id = '${auth.uid.toString()}'", null)
        db.close()
    }
}