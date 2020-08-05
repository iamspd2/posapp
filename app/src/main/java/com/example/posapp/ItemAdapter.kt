package com.example.posapp

//package com.example.top10downloader

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView

class ViewHolder(v: View) {
    val item: TextView = v.findViewById(R.id.eachItem)
    //val tvArtist: TextView = v.findViewById(R.id.tvArtist)
    val price: TextView = v.findViewById(R.id.eachPrice)

}

class ItemAdapter(context: Context, private val resource: Int, private val itemList: List<String>)
    : ArrayAdapter<String>(context, resource) {

    private val inflater = LayoutInflater.from(context)
    var st = arrayListOf<String>("0","0","0","0")

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
            val pl = view.findViewById<ImageButton>(R.id.plus)
            pl.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    val qty = view.findViewById<TextView>(R.id.eachQty)
                    if (qty.text != null) {
                        var n = qty.text.toString().toInt()
                        n += 1
                        st[position] = n.toString()
                        Log.e("ItemAdapter: ",n.toString())
                        qty.setText(n.toString())
                    }
                }
            })

            val mn = view.findViewById<ImageButton>(R.id.minus)
            mn.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    val qty = view.findViewById<TextView>(R.id.eachQty)
                    if (qty.text != null) {
                        var n = qty.text.toString().toInt()
                        if(n >= 1) {
                            n -= 1
                            st[position] = n.toString()
                            Log.e("ItemAdapter: ",n.toString())
                            qty.setText(n.toString())
                        }
                    }
                }
            })
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val str = itemList[position]
        val p = str.indexOf(':')
        val itemName = str.substring(0,p)
        val priceItem = str.substring(p+1)

        viewHolder.item.text = itemName
        //viewHolder.tvArtist.text = currentApp.toString()
        viewHolder.price.text = "Rs. $priceItem"

        return view
    }

    override fun getItem(position: Int): String? {
        return st[position]
    }

    override fun getCount(): Int {
        return itemList.size
    }
}