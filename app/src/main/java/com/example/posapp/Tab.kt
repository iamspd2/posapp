package com.example.posapp

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_tab.*
import java.net.URL
import java.util.ArrayList
import kotlin.properties.Delegates

class Tab : AppCompatActivity() {
    var str: String =""
    private val TAG = "Tab"
    private var downloadData: DownloadData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        val intent = intent
        var name = "Testname"
        val restro = intent.getStringExtra("restro_name")
        val textView2 = findViewById<TextView>(R.id.textView2)

        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email
        val encEmail = email?.replace("""[.#$]""".toRegex(), ",")

        val ref = FirebaseDatabase.getInstance().reference.child(encEmail!!)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                name = dataSnapshot.child("Name").getValue(String::class.java)!!
                Log.e("Name", name)
                textView2.text = name
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

//        val call: Call<String> = ApiClient.getClient.getPhotos()
//        call.enqueue(object : Callback<String> {
//
//            override fun onResponse(call: Call<String>?, response: Response<String>?) {
//                str = response!!.body()!!.toString()
//                Log.e(TAG, "CHECK:"+str)
//            }
//
//            override fun onFailure(call: Call<String>?, t: Throwable?) {
//                //progerssProgressDialog.dismiss()
//                Log.e(TAG,"Failure in retrieving data")
//            }
//        })

//        var urlPath = "https://iamspd2.github.io/menu/index.html"
//        var contents = URL(urlPath).readText()
//        Log.e(TAG,  contents)

        val ur = "https://iamspd2.github.io/menu/index.html"
        downloadData = DownloadData(this, itemListView)
        downloadData?.execute(ur)

        Log.e(TAG, "Data received")
        Log.e(TAG, "Data received: "+str)
        Log.e(TAG, "Data received")

        //val menu = mutableListOf<String>("Tea", "Coffee", "Beer", "Coke")
//        val items = mutableListOf<String>("Tea:20", "Coffee:10", "Beer:15", "Coke:25")
        //var price = mutableListOf<Int>(20,10,15,25)
//        var cart = mutableListOf<String>()

//        val propListView = findViewById<ListView>(R.id.itemListView)
//        val feedAdapter = ItemAdapter(this, R.layout.list_record, items)
//        propListView.adapter = feedAdapter
//        var fd: ItemAdapter? = null
//        fd = downloadData!!.getItem()

        val button2: Button = findViewById<Button>(R.id.button2)
        button2.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                //val cartList = cart
                var fd: ItemAdapter? = null
                val menu = downloadData!!.getItem()
                val price = downloadData!!.getPrice()
                fd = downloadData!!.getList()
                Log.e("Tab", "got items")
                var costs = mutableListOf<String>("0","0","0","0")

                for (i in 0..3) {
                    var num = fd.getItem(i)!!.toInt()
                    var cost = num!!.times(price[i])
                    if (num != 0) {
                        costs[i] = cost.toString()
                    }
                    Log.e("Tab", menu[i]+": "+costs[i])
                }

                //Log.e(TAG, feedAdapter.getItem(1))

                val intent1 = Intent(this@Tab, Checkout::class.java)
                intent1.putStringArrayListExtra("items", menu as ArrayList<String>?)
                intent1.putStringArrayListExtra("cost", costs as ArrayList<String>?)
                intent1.putExtra("restro_name", restro)
                intent1.putExtra("email", encEmail)
                startActivity(intent1)
            }
        })

    }

    fun setData(str: String){
        //str = str
        Log.e(TAG,"Test:"+str)
    }

    companion object {
        private class DownloadData(context: Context, listView: ListView): AsyncTask<String, Void, String>() {
            private val TAG = "DownloadData"

            var propContext : Context by Delegates.notNull()
            var propListView : ListView by Delegates.notNull()
            var it : MutableList<String>
            var pr : MutableList<Int>
            lateinit var feedAdapter: ItemAdapter
            //var pr = mutableListOf<String>()

            init {
                propContext = context
                propListView = listView
                it = mutableListOf()
                pr = mutableListOf<Int>()
                //feedAdapter = null
            }

            fun getItem(): MutableList<String> {
                return it
            }

            fun getList(): ItemAdapter {
                return feedAdapter
            }

            fun getPrice(): MutableList<Int> {
                return pr
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                //Log.e("onPostExecute", result)
                val pattern = "\".+\"".toRegex()

                val matches = pattern.findAll(result)
                var i = 0


                matches.forEach { f ->

                    val m = f.value
                    val idx = f.range
                    //Log.e("onPostExecute", m)
                    if (i % 2 == 0)
                        it.add(m.replace("\"",""))
                    else
                        pr.add((m.replace("\"","")).toInt())
                    i+=1
                }
                var items = mutableListOf<String>()
                for (i in 0..3) {
                    items.add(it[i]+":"+pr[i].toString())
                    Log.e("onPostExecute",items[i])
                }

                //val propListView = findViewById<ListView>(R.id.itemListView)
                feedAdapter = ItemAdapter(propContext, R.layout.list_record, items)
                propListView.adapter = feedAdapter



                //val button2: Button = findViewById<Button>(R.id.button2)
//                button2.setOnClickListener(object : View.OnClickListener {
//                    override fun onClick(v: View?) {
//                        //val cartList = cart
//                        var costs = mutableListOf<String>("0", "0", "0", "0")
//
//                        for (i in 0..3) {
//                            var num = feedAdapter.getItem(i)!!.toInt()
//                            var cost = num!!.times(price[i])
//                            if (cost != null) {
//                                costs[i] = cost.toString()
//                            }
//                            Log.e("Tab", menu[i] + ": " + costs[i])
//                        }
//
//                        //Log.e(TAG, feedAdapter.getItem(1))
//
//                        val intent1 = Intent(this@Tab, Checkout::class.java)
//                        intent1.putStringArrayListExtra("items", menu as ArrayList<String>?)
//                        intent1.putStringArrayListExtra("cost", costs as ArrayList<String>?)
//                        startActivity(intent1)
//                    }
//                })

//                it.forEach {
//                    Log.e("onPostExecute", it)
//                }
//                pr.forEach {
//                    Log.e("onPostExecute", it)
//                }

            }

            override fun doInBackground(vararg url: String?): String {
                Log.d(TAG, "doInBackground: starts with ${url[0]}")
//                val rssFeed = downloadXML(url[0])
                var urlPath = "https://iamspd2.github.io/menu/index.html"
                var contents = URL(urlPath).readText()
                Log.e(TAG,  contents)
                if (contents.isEmpty()) {
                    Log.e(TAG, "doInBackground: Error downloading")
                }
                return contents
            }

//            private fun downloadXML(urlPath: String?): String {
//                return URL(urlPath).readText()
//            }
        }
    }
}
