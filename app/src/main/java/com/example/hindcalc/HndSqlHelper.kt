package com.example.hindcalc

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.annotation.ContentView
import kotlin.concurrent.thread

class HndSqlHelper(context: Context): SQLiteOpenHelper(context,"HndDataBase",null,1) {

    companion object{
        val Table_Name_HndInfo:String="HND_PLYER_INFO"
        val Table_Name_Details:String="DETAILS_PLAYER_INFO"
        val Game_id:String="_id"
        val Game_Turn:String="GAME_TURN"
        //val Player_ID:String="_ID"
        val Player_Name:String="PLAYER_NAME"
        val Player_Score:String="PLAYER_SCORE"
        val Player_Final_Score:String="PLAYER_RESULT"
    }
    val selctCols:kotlin.Array<String> = arrayOf(HndSqlHelper.Player_Name,
            HndSqlHelper.Player_Score,HndSqlHelper.Player_Final_Score)

    private  val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${Table_Name_HndInfo} ( ${Game_id} INTEGER PRIMARY KEY AUTOINCREMENT, ${Game_Turn} INTEGER NOT NULL," +
                " ${Player_Name} TEXT NOT NULL, ${Player_Score} INTEGER,  ${Player_Final_Score} INTEGER)"

    private  val SQL_CREATE_ENTRIES1 =
            "CREATE TABLE ${Table_Name_Details} ( ${Game_id} INTEGER PRIMARY KEY AUTOINCREMENT, ${Game_Turn} INTEGER NOT NULL," +
                    " ${Player_Name} TEXT NOT NULL, ${Player_Score} INTEGER,  ${Player_Final_Score} INTEGER)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${Table_Name_HndInfo}"
    private val SQL_DELETE_ENTRIES1 = "DROP TABLE IF EXISTS ${Table_Name_Details}"
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
        db?.execSQL(SQL_CREATE_ENTRIES1)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        db?.execSQL(SQL_DELETE_ENTRIES1)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db,oldVersion,newVersion)
    }
    fun AddPlayerInfo(TabName:String,name:String,GScore:Int,SumScore:Int):Long{
    val db=this.writableDatabase
    val v=ContentValues().apply {
     put(Player_Name,name)
     put(Player_Score,SumScore)
     put(Player_Final_Score,GScore)
     put(Game_Turn,1)
    }
        var a =db.insert(TabName,null,v)
        //Log.d("tag1","Insert:"+a.toString())
return a
    }

    fun getPlayers(TableName:String,PlayerName:String=""): Cursor? {
        val db = this.readableDatabase
        if(PlayerName=="") {
            return db.query(TableName, null, null, null, null, null, Player_Final_Score + " ASC")
        }else{
            val cur:Cursor =db.query(TableName,null,HndSqlHelper.Player_Name+"='${PlayerName}'", null,null,null,HndSqlHelper.Game_id+" ASC")
            Log.d("tag4","Row No: ${cur.count}")
            return cur
        }

    }
    fun UpdatePlayerInfo(name:String,GScore:Int,SumScore:Int):Cursor?
    {
        //Toast.makeText(this, "update", Toast.LENGTH_SHORT).show()
        var db=this.writableDatabase
        val v=ContentValues().apply {
            put(Player_Name,name)
            put(Player_Score,GScore)
            put(Player_Final_Score,SumScore)
            put(Game_Turn,1)
        }
        var a=db.update(Table_Name_HndInfo,v, "${Player_Name} = '${name}'",null)
        //return null
        Log.d("tag1","update: ${name}"+a.toString())
        return getPlayers(HndSqlHelper.Table_Name_HndInfo)
        //db.close()
//Thread.sleep(10000)
        //db=this.readableDatabase
        //return db.query(Table_Name_HndInfo,null,null,null,null,null,Player_Score+" ASC")

    }
    fun clearTable(TableName: String)
    {
        var db=this.writableDatabase
        val a=db.delete(TableName,null,null)
        Log.d("tag3"," Deleted: ${a} Columns")

    }
}