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

class ProfileHolder(v: View) {
    val name: TextView = v.findViewById(R.id.restroName)
    val cuisines: TextView = v.findViewById(R.id.restroCuisine)
    val timings: TextView = v.findViewById(R.id.restroTiming)
    val ratings: TextView = v.findViewById(R.id.restroRating)
    //val tvArtist: TextView = v.findViewById(R.id.tvArtist)
//    val price: TextView = v.findViewById(R.id.eachPrice)

}

class ProfileAdapter(val n: String, context: Context, private val resource: Int, private val itemList: List<String>)
    : ArrayAdapter<String>(context, resource) {

    private val inflater = LayoutInflater.from(context)
    var st = arrayListOf<String>("0","0","0","0")

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: RestroHolder
        val nm = n
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = RestroHolder(view)
            view.tag = viewHolder

            val pl = view.findViewById<Button>(R.id.openTab)
            pl.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {

                    val intent1 = Intent(context, Tab::class.java)
                    intent1.putExtra("user_name", nm)
//                    intent1.putStringArrayListExtra("cost", costs as ArrayList<String>?)
                    context.startActivity(intent1)
                    Log.e("Touch","Touch")
                }
            })

        } else {
            view = convertView
            viewHolder = view.tag as RestroHolder
        }

//        val str = itemList[position]
//        val p = str.indexOf(':')
//        val itemName = str.substring(0,p)
//        val priceItem = str.substring(p+1)
        val i = itemList[position]
        val str = i.split(":#:")
        viewHolder.name.text = str[0]
        viewHolder.cuisines.text = str[1]
        viewHolder.timings.text = str[2]
        if(str[3] == "0")
            viewHolder.ratings.text = "Not Rated"
        else
            viewHolder.ratings.text = str[3]
        //viewHolder.name.text = str[0]
        //Log.e("Adapter test", itemList[0])
        //viewHolder.tvArtist.text = currentApp.toString()
        //viewHolder.price.text = "Rs. $priceItem"

        return view
    }

//    override fun getItem(position: Int): String? {
//        return st[position]
//    }

    override fun getCount(): Int {
        return itemList.size
    }
}