package com.example.hindcalc

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.lang.ClassCastException

//import com.pioneersacademy.alkaff.firstapplication.helpers.MySqlHelper

//cursorAdapter = SimpleCursorAdapter(
//applicationContext,
//R.layout.list_item_custom,
//cursor,
//fromCursor,
//to,
//SimpleAdapter.NO_SELECTION
open  class MyCustomCursorAdapter(context: Context, cursor:Cursor?):CursorAdapter(context,cursor,0) {   //
    //private lateinit var onButClickListner:Callback
//    public interface onbutclicklistner{
//        fun onbutclick()
//    }
//    init {
//        if (context !is onbutclicklistner){
//        //throw
//        }else
//        {
//            //this = context as onbutclicklistner
//        }
//    }

    interface ListViewListner {
        fun FillPlayerDetails(text:String)
    }

    private var LVListner: ListViewListner? = null
init {
    try {
        LVListner = context as ListViewListner
    } catch (e: ClassCastException) {
        throw ClassCastException("${context?.toString()} must implement MyListFragment.ItemListener")
    }
}



        private  lateinit var  sqlhelper:HndSqlHelper
    private lateinit var layoutInflater:LayoutInflater
    private class ViewHolder {
        var textViewPName: TextView? = null
        var textViewSumtxt:TextView? = null
        var textviewPScore:EditText?=null
        var textviewFinalScore:TextView?=null
        var buttonHist:Button? = null
    }

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {

        layoutInflater = LayoutInflater.from(context)
        val newView = layoutInflater.inflate(R.layout.playerinfo,parent,false)
        val viewHolder = ViewHolder()
        viewHolder.textViewPName = newView.findViewById(R.id.pname)
        viewHolder.textViewSumtxt = newView.findViewById(R.id.FinalScore)
        viewHolder.textviewPScore=newView.findViewById(R.id.PlayerScore)
        viewHolder.textviewFinalScore = newView.findViewById(R.id.Sumtxt)
        viewHolder.buttonHist = newView.findViewById(R.id.PHistory)

        // store the viewHolder object with the new view using the tag
        newView.tag = viewHolder
        Log.d("tag6","${viewHolder.textViewPName?.text.toString()}")
        return  newView

    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {

        // get the viewHolder from the newView object via the Tag object
        val viewHolder = view!!.tag as ViewHolder
        viewHolder.textViewPName?.text = cursor?.getString(cursor.getColumnIndex(HndSqlHelper.Player_Name))
        viewHolder.textViewSumtxt?.text = cursor?.getString(cursor.getColumnIndex(HndSqlHelper.Player_Score))
        viewHolder.textviewFinalScore?.text=cursor?.getString(cursor.getColumnIndex(HndSqlHelper.Player_Final_Score))
        Log.d("tag7","${viewHolder.textViewPName?.text.toString()}")
        //viewHolder.buttonHist?.text = cursor?.getString(cursor.getColumnIndex())

        viewHolder.buttonHist?.setOnClickListener{
            //val btn = it as Button
            //Toast.makeText(context,"Button clicked ${btn.text.toString()}",Toast.LENGTH_SHORT).show()
//            val binding:HndmainactivityBinding= HndmainactivityBinding.inflate(layoutInflater)
//
//            val detailsList:ListView?=binding.PlayerDetailList
//            //datailsList.adapter.
//            sqlhelper= HndSqlHelper(context!!)
//            val DetailsCur:Cursor?=sqlhelper.getPlayers(HndSqlHelper.Table_Name_Details,viewHolder.textViewPName?.text.toString())
//
//            val ArrAdp=MyCustomCursorAdapter(context.applicationContext,cursor)
//            view.tag=ArrAdp
//            binding.PlayerDetailList?.adapter=ArrAdp
//
//            Log.d("tag5","cur 1count: ${cursor?.count} is equal ArrAdp count: ${binding.PlayerDetailList?.adapter?.count}")
//            ArrAdp.notifyDataSetChanged()

            LVListner?.FillPlayerDetails(viewHolder.textViewPName?.text.toString())
        }

    }

}