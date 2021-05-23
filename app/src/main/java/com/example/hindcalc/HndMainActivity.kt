package com.example.hindcalc
import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.iterator
import com.example.hindcalc.databinding.HndmainactivityBinding
//import com.pioneersacademy.alkaff.firstapplication.MyCustomCursorAdapter
//import com.example.hindcalc.MyCustomCursorAdapterCls
import java.util.ArrayList

class HndMainActivity : AppCompatActivity(),MyCustomCursorAdapter.ListViewListner {
    private lateinit var binding:HndmainactivityBinding
    private lateinit var sqlHelper:HndSqlHelper
    var arrlist:ArrayList<String> = ArrayList()
    var cursor:Cursor?=null
    lateinit var ArrAdpt: MyCustomCursorAdapter

 val fromcursor: kotlin.Array<String> = arrayOf(HndSqlHelper.Player_Name,
        HndSqlHelper.Player_Score,HndSqlHelper.Player_Final_Score)
    val to: IntArray =
            intArrayOf(R.id.pname, R.id.FinalScore, R.id.Sumtxt)
    lateinit var mylist:ListView
    private lateinit var DetailsCur:Cursor
    private lateinit var HistoryApt:MyCustomCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HndmainactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mylist=ListView(applicationContext)
        mylist=findViewById(R.id._dynamic) as ListView
        //ArrAdpt= ArrayAdapter(this, R.layout.playerinfo, R.id.pname, arrlist)
        sqlHelper= HndSqlHelper(applicationContext)
        cursor=sqlHelper.getPlayers(HndSqlHelper.Table_Name_HndInfo)



        mylist.setBackgroundColor(Color.WHITE)
//            .readableDatabase.query(
//            HndSqlHelper.Table_Name_HndInfo,null,null,null,null,null,
//            HndSqlHelper.Player_Final_Score +" ASC")

        ArrAdpt= MyCustomCursorAdapter(this@HndMainActivity,cursor) //,R.layout.playerinfo,cursor,fromcursor,to,CursorAdapter.NO_SELECTION)
        binding.Dynamic.adapter=ArrAdpt
        ArrAdpt.notifyDataSetChanged()


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

                cursor=sqlHelper.UpdatePlayerInfo(pdname.text.toString(), EDT.text.toString().toInt(), nScore)


                sqlHelper.AddPlayerInfo(HndSqlHelper.Table_Name_Details,pdname.text.toString(),EDT.text.toString().toInt(), nScore)

//                cursor=sqlHelper.readableDatabase.query(
//                    HndSqlHelper.Table_Name_HndInfo,null,null,null,null,null,
//                    HndSqlHelper.Player_Score +" ASC")
                //cursor=sqlHelper.getPlayers()
                ArrAdpt.changeCursor(cursor)
                //binding.Dynamic.adapter=ArrAdpt
                ArrAdpt.notifyDataSetChanged()
                EDT.setText("")
                //ET.setBackgroundColor(Color.WHITE)
            }
        }catch(e:Exception){
            Toast.makeText(this, "Error: ${e.toString()}", Toast.LENGTH_LONG).show()
        }

    }


//            var ln0 =mylist.getItemIdAtPosition(0).toInt()
//            //var ln0=mylist.adapter.getItemId(0).toInt()
//            mylist.setBackgroundColor(Color.WHITE)
//            //Log.d("tag2","First: ${mylist.get(0).findViewById<TextView>(R.id.pname).text.toString()} Last:${mylist.get(mylist.count-1).findViewById<TextView>(R.id.pname).text.toString()} ")
//            Log.d("tag2","First: ${ArrAdpt.getView(0,null, mylist).findViewById<TextView>(R.id.pname).text.toString()} ")
//
//            //mylist.get(0).setBackgroundColor(Color.GREEN)
//
//            val cur:Cursor= mylist.getItemAtPosition(0) as Cursor
//            val i= cursor?.position ?: Int
//            //val ii=i as Int
//            val v:View= ArrAdpt.getView(0,mylist.get(mylist[0].top) as View, mylist)
//                    //mylist.get(mylist[0].top) as View
//            v.setBackgroundColor(Color.GREEN)
//            //Toast.makeText(applicationContext,"${mylist.get(0).findViewById<TextView>(R.id.pname).text.toString()}")
//            mylist.get(mylist.count-1).setBackgroundColor(Color.RED)

}
        findViewById<Button>(R.id.newGame).setOnClickListener{
            //Toast.makeText(this, "new game", Toast.LENGTH_LONG).show()
            mylist.setBackgroundColor(Color.WHITE)
            //mylist.refreshDrawableState()
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

                cursor=sqlHelper.UpdatePlayerInfo(pdname.text.toString(),0, 0)

                //cursor= sqlHelper.getPlayers()
                ArrAdpt.changeCursor(cursor)
               // binding.Dynamic.adapter=ArrAdpt
                ArrAdpt.notifyDataSetChanged()
                    EDT.setText("")
                //}
            }
            sqlHelper.clearTable(HndSqlHelper.Table_Name_Details)

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
        val res=sqlHelper.AddPlayerInfo(HndSqlHelper.Table_Name_HndInfo,binding.PlyerName.text.toString(),0,0)
        cursor=sqlHelper.getPlayers(HndSqlHelper.Table_Name_HndInfo)
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

    override fun FillPlayerDetails(text: String) {

        val DetailsCur=sqlHelper.getPlayers(HndSqlHelper.Table_Name_Details,text)

        val HistoryApt =MyCustomCursorAdapter(this@HndMainActivity,DetailsCur)

        HistoryApt.changeCursor(DetailsCur)
        binding.PlayerDetailList?.adapter=HistoryApt

        //Log.d("tag5","cur 1count: ${cursor?.count} is equal ArrAdp count: ${binding.PlayerDetailList?.adapter?.count}")
        ArrAdpt.notifyDataSetChanged()

    }

}