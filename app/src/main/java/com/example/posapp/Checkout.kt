package com.example.posapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue


class Checkout : AppCompatActivity() {
    val UPI_PAYMENT = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val intent = intent
        val cart = intent.getStringArrayListExtra("items")
        val restro = intent.getStringExtra("restro_name")
        val email = intent.getStringExtra("email")
        Log.e("Checkout", restro)
        val costs = intent.getStringArrayListExtra("cost")
        val textView = findViewById<TextView>(R.id.textView5)
        val textView3 = findViewById<TextView>(R.id.textView6)
        var t = 0
        var tot: Double = 0.05
        var itemDB = cart[0]
        for (i in 1..3) {
            itemDB += "@#" + cart[i]
            Log.e("Checkout", cart[i]+": "+costs[i])
        }
        for(i in 0..3) {
            var cost = costs[i].toInt()
            textView.append(cart[i])
            textView.append("\n")
            textView3.append("Rs. $cost.00\n")
            t+=cost
        }
        textView.append("\nGST\n")
        tot = tot.times(t)
        var gst= String.format("%.2f",tot)
        textView3.append("\nRs. $gst\n")
        tot = tot + t
        var total= String.format("%.2f",tot)
        textView.append("\nTotal\n")
        textView3.append("\nRs. $total\n")
        val textView2 = findViewById<TextView>(R.id.textView3)
        textView2.append("Rs. $total")

        val hashMap : HashMap<String,Any> = HashMap()
        hashMap["restro"] = restro
        hashMap["cost"] = "Rs. $total"
        hashMap["items"] = itemDB
        hashMap["time"] = ServerValue.TIMESTAMP.toString()
        val orderID = randomAlphaNumeric(7)
        FirebaseDatabase.getInstance().reference.child(email).child("orders").child(orderID).updateChildren(hashMap)



        val button: Button = findViewById<Button>(R.id.checkout)
        button.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                payUsingUpi("Pay",total)
            }
        })

    }

    fun randomAlphaNumeric(count: Int): String {
        var count = count
        val ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val builder = StringBuilder()
        while (count-- != 0) {
            val character = (Math.random() * ALPHA_NUMERIC_STRING.length).toInt()
            builder.append(ALPHA_NUMERIC_STRING[character])
        }
        return builder.toString()
    }

    fun payUsingUpi(
        name: String,
//        upiId: String,
//        note: String,
        amount: String
    ) {
        val uri: Uri = Uri.Builder()
            .scheme("upi")
            .authority("pay")
            .appendQueryParameter("pa", "dash.amiya5@oksbi") // virtual ID
            .appendQueryParameter("pn", "Amiya") // name
            .appendQueryParameter("mc", "your-merchant-code") // optional
            .appendQueryParameter("tr", "your-transaction-ref-id") // optional
            .appendQueryParameter("tn", "your-transaction-note") // any note about payment
            .appendQueryParameter("am", "1.00") // amount
            .appendQueryParameter("cu", "INR") // currency
            .appendQueryParameter("url", "your-transaction-url") // optional
            .build()
        val GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"
        val GOOGLE_PAY_REQUEST_CODE = 123

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME)
        startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("Back", "response $resultCode")
        Log.e("Back", "response $requestCode")
        Log.e("Back", "response ${data.toString()}")
        when (requestCode) {
            UPI_PAYMENT -> if (Activity.RESULT_OK == resultCode || resultCode == 11) {
                if (data != null) {
                    val trxt = data.getStringExtra("response")
                    Log.e("UPI", "onActivityResult: $trxt")
                    val dataList: ArrayList<String> = ArrayList()
                    dataList.add(trxt)
                    upiPaymentDataOperation(dataList)
                } else {
                    Log.e("UPI", "onActivityResult: " + "Return data is null")
                    val dataList: ArrayList<String> = ArrayList()
                    dataList.add("nothing")
                    upiPaymentDataOperation(dataList)
                }
            } else {
                //when user simply back without payment
                Log.e("UPI", "onActivityResult: " + "Return data is null")
                val dataList: ArrayList<String> = ArrayList()
                dataList.add("nothing")
                upiPaymentDataOperation(dataList)
            }
        }
    }

    private fun upiPaymentDataOperation(data: ArrayList<String>) {
        if (isConnectionAvailable(this)) {
            var str = data[0]
            Log.e("UPIPAY", "upiPaymentDataOperation: $str")
            var paymentCancel = ""
            if (str == null) str = "discard"
            var status = ""
            var approvalRefNo = ""
            val response = str.split("&".toRegex()).toTypedArray()
            for (i in response.indices) {
                val equalStr =
                    response[i].split("=".toRegex()).toTypedArray()
                if (equalStr.size >= 2) {
                    if (equalStr[0].toLowerCase() == "Status".toLowerCase()) {
                        status = equalStr[1].toLowerCase()
                    } else if (equalStr[0]
                            .toLowerCase() == "ApprovalRefNo".toLowerCase() || equalStr[0]
                            .toLowerCase() == "txnRef".toLowerCase()
                    ) {
                        approvalRefNo = equalStr[1]
                    }
                } else {
                    paymentCancel = "Payment cancelled by user."
                }
            }
            if (status == "success") {
                //Code to handle successful transaction here.
                Toast.makeText(this, "Transaction successful.", Toast.LENGTH_SHORT)
                    .show()
                Log.e("UPI", "payment successfull: $approvalRefNo")
                val intent = Intent(this, MapsActivity2::class.java)
                startActivity(intent)
            } else if ("Payment cancelled by user." == paymentCancel) {
                Toast.makeText(this, "Payment cancelled by user.", Toast.LENGTH_SHORT)
                    .show()
                Log.e("UPI", "Cancelled by user: $approvalRefNo")
            } else {
                Toast.makeText(
                    this,
                    "Transaction failed.Please try again",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("UPI", "failed payment: $approvalRefNo")
            }
        } else {
            Log.e("UPI", "Internet issue: ")
            Toast.makeText(
                this,
                "Internet connection is not available. Please check and try again",
                Toast.LENGTH_SHORT
            ).show()
        }
        val intent = Intent(this, MapsActivity2::class.java)
        startActivity(intent)
    }

    fun isConnectionAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val netInfo = connectivityManager.activeNetworkInfo
            if (netInfo != null && netInfo.isConnected
                && netInfo.isConnectedOrConnecting
                && netInfo.isAvailable
            ) {
                return true
            }
        }
        return false
    }
}