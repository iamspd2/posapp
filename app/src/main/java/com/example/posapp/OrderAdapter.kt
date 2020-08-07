package com.example.posapp

//package com.example.top10downloader

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class OrderHolder(v: View) {
    val name: TextView = v.findViewById(R.id.order1)
    val phone: TextView = v.findViewById(R.id.order2)
//    val cuisines: TextView = v.findViewById(R.id.restroCuisine)
//    val timings: TextView = v.findViewById(R.id.restroTiming)
//    val ratings: TextView = v.findViewById(R.id.restroRating)
    //val tvArtist: TextView = v.findViewById(R.id.tvArtist)
//    val price: TextView = v.findViewById(R.id.eachPrice)

}

class OrderAdapter(context: Context, private val resource: Int, private val itemList: List<Information>)
    : ArrayAdapter<Information>(context, resource) {

    private val inflater = LayoutInflater.from(context)
    var st = arrayListOf<String>("0","0","0","0")

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: OrderHolder
//        val nm = n
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = OrderHolder(view)
            view.tag = viewHolder


        } else {
            view = convertView
            viewHolder = view.tag as OrderHolder
        }

        val i = getItem(position)



        viewHolder.name.text = i?.name
        viewHolder.phone.text = i?.phone

        //Log.e("Adapter test", itemList[0])
        //viewHolder.tvArtist.text = currentApp.toString()
        //viewHolder.price.text = "Rs. $priceItem"

        return view
    }

    override fun getCount(): Int {
        return itemList.size
    }
}