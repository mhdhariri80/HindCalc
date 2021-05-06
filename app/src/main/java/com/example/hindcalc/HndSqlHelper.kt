package com.example.hindcalc

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import androidx.annotation.ContentView

class HndSqlHelper(context: Context): SQLiteOpenHelper(context,"HndDataBase",null,1) {

    companion object{
        val Table_Name_HndInfo:String="HND_PLYER_INFO"
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

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${Table_Name_HndInfo}"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db,oldVersion,newVersion)
    }
    fun AddPlayerInfo(name:String,GScore:Int,SumScore:Int):Long{
    val db=this.writableDatabase
    val v=ContentValues().apply {
     put(Player_Name,name)
     put(Player_Score,SumScore)
     put(Player_Final_Score,GScore)
     put(Game_Turn,1)
    }

        var a =db.insert(Table_Name_HndInfo,null,v)
        Log.d("tag1","Insert:"+a.toString())
return a
    }

    fun getPlayers(): Cursor? {
        val db = this.readableDatabase
        return  db.query(Table_Name_HndInfo,null,null,null,null,null,null)
    }
    fun UpdatePlayerInfo(name:String,GScore:Int,SumScore:Int)
    {
        //Toast.makeText(this, "update", Toast.LENGTH_SHORT).show()
        val db=this.writableDatabase
        val v=ContentValues().apply {
            put(Player_Name,name)
            put(Player_Score,GScore)
            put(Player_Final_Score,SumScore)
            put(Game_Turn,1)
        }
        val a=db.update(Table_Name_HndInfo,v, "${Player_Name} = '${name}'",null)
        Log.d("tag1","update:"+a.toString())
    }
    fun clearTable()
    {
        val db=this.writableDatabase

    }
}