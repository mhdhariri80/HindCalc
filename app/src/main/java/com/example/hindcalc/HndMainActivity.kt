package com.example.hindcalc

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.core.view.iterator
import com.example.hindcalc.databinding.HndmainactivityBinding
import java.lang.reflect.Array
import java.util.ArrayList
import kotlin.concurrent.thread
import kotlin.math.E

class HndMainActivity : AppCompatActivity() {
private lateinit var binding:HndmainactivityBinding
    private lateinit var sqlHelper:HndSqlHelper
    var arrlist:ArrayList<String> = ArrayList()
    var cursor:Cursor?=null
    lateinit var ArrAdpt: CursorAdapter
 val fromcursor: kotlin.Array<String> = arrayOf(HndSqlHelper.Player_Name,
        HndSqlHelper.Player_Score,HndSqlHelper.Player_Final_Score)
    val to: IntArray =
            intArrayOf(R.id.pname, R.id.Sumtxt,R.id.FinalScore)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HndmainactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //ArrAdpt= ArrayAdapter(this, R.layout.playerinfo, R.id.pname, arrlist)
        sqlHelper= HndSqlHelper(applicationContext)
        cursor=sqlHelper.getPlayers()
        ArrAdpt=SimpleCursorAdapter(this,R.layout.playerinfo,cursor,fromcursor,to,CursorAdapter.NO_SELECTION)
        binding.Dynamic.adapter=ArrAdpt
        ArrAdpt.notifyDataSetChanged()
        var mylist:ListView=ListView(applicationContext)
        mylist=findViewById(R.id._dynamic) as ListView

        findViewById<Button>(R.id.Calc).setOnClickListener(){
    //for (i in [0..mylist])

    //Toast.makeText(applicationContext, mylist.get(0).id.toString(),Toast.LENGTH_LONG)

    for(ET:View in mylist) {
        //var ET:View=Etv //mylist.getChildAt(1)// as ViewGroup
        var pdname: TextView = ET.findViewById<TextView>(R.id.pname)
        var EDT: EditText = ET.findViewById<EditText>(R.id.PlayerScore)
        var TxtV: TextView = ET.findViewById<TextView>(R.id.Sumtxt)
        //Log.d("tag1","myString: ${EDT.text.toString()}")
        //ET.findViewById<TextView>(R.id.FinalScore).setText(EDT.text.toString())

        var nScore: Int
        var fScor: Int
        try {
            if (EDT.text.isNotEmpty()) {
                nScore = EDT.text.toString().toInt() + TxtV.text.toString().toInt()
                ET.findViewById<TextView>(R.id.Sumtxt).setText(nScore.toString())
                ET.findViewById<TextView>(R.id.FinalScore).setText(EDT.text.toString())

                sqlHelper.UpdatePlayerInfo(pdname.text.toString(), EDT.text.toString().toInt(), nScore)
                EDT.setText("")
            }
        }catch(e:Exception){
            Toast.makeText(this, "Error: ${e.toString()}", Toast.LENGTH_LONG).show()
        }

    }

}

        findViewById<Button>(R.id.newGame).setOnClickListener{
            //Toast.makeText(this, "new game", Toast.LENGTH_LONG).show()
            for(ET:View in mylist) {
                //var ET:View=Etv //mylist.getChildAt(1)// as ViewGroup
                var pdname: TextView = ET.findViewById<TextView>(R.id.pname)
                var EDT: EditText = ET.findViewById<EditText>(R.id.PlayerScore)
                //var TxtV: TextView = ET.findViewById<TextView>(R.id.Sumtxt)
                //Log.d("tag1","myString: ${EDT.text.toString()}")
                //ET.findViewById<TextView>(R.id.FinalScore).setText(EDT.text.toString())

                var nScore: Int
                var fScor: Int
                //if(EDT.text.isNotEmpty()) {
                  //  nScore = EDT.text.toString().toInt() + TxtV.text.toString().toInt()
                    //ET.findViewById<TextView>(R.id.Sumtxt).setText(nScore.toString())
                   // ET.findViewById<TextView>(R.id.FinalScore).setText(EDT.text.toString())

                    sqlHelper.UpdatePlayerInfo(pdname.text.toString(),0, 0)
                ArrAdpt.changeCursor(cursor)
                binding.Dynamic.adapter=ArrAdpt
                ArrAdpt.notifyDataSetChanged()
                    EDT.setText("")
                //}
            }


        }



    }

    fun AddPlayer(view: View) {
        //SaveDataIntoMemeory()
        //simpleAdapter = SimpleAdapter(this,datamap,R.layout.playerinfo,from,to)
//binding.Dynamic.adapter=simpleAdapter
//        simpleAdapter.notifyDataSetChanged()
//        binding.PlyerName.setText("")

        //arrlist.add(binding.PlyerName.text.toString())
        //ArrAdpt = ArrayAdapter(this, R.layout.playerinfo, R.id.pname, arrlist)

       // binding.Dynamic.adapter = ArrAdpt
       // ArrAdpt.notifyDataSetChanged()

        //----------------
       // sqlHelper= HndSqlHelper(applicationContext)
        val res=sqlHelper.AddPlayerInfo(binding.PlyerName.text.toString(),0,0)
        cursor=sqlHelper.getPlayers()
        if(cursor==null){
            Toast.makeText(this,"cursor null", Toast.LENGTH_LONG).show()
        }else {
            Toast.makeText(this, "cur not null", Toast.LENGTH_LONG).show()
            ArrAdpt.changeCursor(cursor)
            binding.Dynamic.adapter=ArrAdpt
            ArrAdpt.notifyDataSetChanged()
        }
        binding.PlyerName.setText("")
    }
    fun newGame(){

    }
}