package com.example.posapp

//package com.example.top10downloader

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.firebase.database.ValueEventListener

class OrderHolder(v: View) {
    val name: TextView = v.findViewById(R.id.order1)
    val phone: TextView = v.findViewById(R.id.order2)
}

//class OrderAdapter(context: Context, private val resource: Int, private val itemList: ArrayList<Information>)
//    : ArrayAdapter<Information>(context, resource) {


class OrderAdapter(context: Context, private val resource: Int, private val itemList: List<List<String>>)
    : ArrayAdapter<List<String>>(context, resource) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: OrderHolder
//        val nm = n
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = OrderHolder(view)
            view.tag = viewHolder

            Log.e("Test","Yo")
//            val i = itemList[position]
//
//            viewHolder.name.text = "DSD"
//            Log.e("Adapter test", i.name)
//            viewHolder.phone.text = i.phone
//            Log.e("Adapter test", i.phone)


        } else {
            view = convertView
            viewHolder = view.tag as OrderHolder
        }


        val i = itemList[position]
        viewHolder.name.text = i[0]
        viewHolder.phone.text = i[1]
//        Log.e("Test", i)

//        viewHolder.name.text = i
//        Log.e("Test", i.name)
//        viewHolder.phone.text = i.phone
//        Log.e("Test", i.phone)

        //Log.e("Adapter test", itemList[0])
        //viewHolder.tvArtist.text = currentApp.toString()
        //viewHolder.price.text = "Rs. $priceItem"

        return view
    }

    override fun getCount(): Int {
        return itemList.size
    }
}