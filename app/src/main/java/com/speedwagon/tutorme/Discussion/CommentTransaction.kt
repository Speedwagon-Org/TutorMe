package com.speedwagon.tutorme.Discussion

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.text.Editable

class CommentTransaction(context: Context)  {
    private val myDBHelper = DBHelperDiscussion(context, null)
    private val dbwirte = myDBHelper.writableDatabase

    @SuppressLint("Range")

    fun viewAllName(): ArrayList<ItemDiscussion>{
        var alldata = ItemDiscussion()
        var nameList:ArrayList<ItemDiscussion> =  ArrayList<ItemDiscussion>()

        //ambil data dari sqlite
        val selectQuery = "SELECT ${DBHelperDiscussion.USERNAME}, ${DBHelperDiscussion.TEXT}" +
                " FROM ${DBHelperDiscussion.TABLE_NAME}"
        val db = myDBHelper.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        if (cursor.moveToFirst()) {
            do {
                alldata.username = cursor.getString(cursor.getColumnIndex(DBHelperDiscussion.USERNAME))
                alldata.Text = cursor.getString(cursor.getColumnIndex(DBHelperDiscussion.TEXT))
            } while (cursor.moveToNext())
            nameList.add(alldata)
        }
        return nameList
    }


    fun addUser(item: ItemDiscussion):Long{
        val db = myDBHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(DBHelperDiscussion.USERNAME, item.username)
        contentValues.put(DBHelperDiscussion.TEXT, item.Text)
        val success = db.insert(DBHelperDiscussion.TABLE_NAME,
            null, contentValues)
        db.close()
        return success
    }

    fun beginUserTransaction()
    {
        dbwirte.beginTransaction()
    }
    fun successUserTransaction()
    {
        dbwirte.setTransactionSuccessful()
    }
    fun endUserTransaction()
    {
        dbwirte.endTransaction()
    }
    fun addUserTransaction(item: ItemDiscussion):Unit{
        val sqlString = "INSERT INTO ${DBHelperDiscussion.TABLE_NAME} " +
                "(${DBHelperDiscussion.USERNAME}" +
                ",${DBHelperDiscussion.TEXT}" +
                ") VALUES (?,?)"
        val myStatement = dbwirte.compileStatement(sqlString)
        myStatement.bindString(1,item.username)
        myStatement.bindString(2,item.Text)
        myStatement.execute()
        myStatement.clearBindings()
    }

    fun deleteAll():Int{
        val db = myDBHelper.writableDatabase
        val success = db.delete(DBHelperDiscussion.TABLE_NAME,"",null)
        db.close()
        return success
    }
}