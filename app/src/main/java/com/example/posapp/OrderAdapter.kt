package com.example.posapp

//package com.example.top10downloader

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class OrderHolder(v: View) {
    val restro: TextView = v.findViewById(R.id.orderRestro)
    val items: TextView = v.findViewById(R.id.orderItems)
    val cost: TextView = v.findViewById(R.id.orderAmount)
    val time: TextView = v.findViewById(R.id.orderTime)
}

//class OrderAdapter(context: Context, private val resource: Int, private val itemList: ArrayList<Information>)
//    : ArrayAdapter<Information>(context, resource) {


class OrderAdapter(context: Context, private val resource: Int, private val itemList: List<Information>)
    : ArrayAdapter<Information>(context, resource) {

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

            val i = itemList[position]
            viewHolder.restro.text = i.restro
            viewHolder.cost.text = i.cost
            viewHolder.time.text = i.time

            val st = i.items.split("@#@")
            for (i in 0..st.size-1) {
                //Log.e("Orders",s)
                if (i == 0)
                    viewHolder.items.append(st[i])
                else
                    viewHolder.items.append("\n"+st[i])
            }
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