package com.example.posapp

//package com.example.posapp
//

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*


//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.Toolbar
//import android.view.Menu
//import android.view.MenuItem
//import android.view.View
//import android.widget.*
//import androidx.appcompat.app.AppCompatActivity
//import com.bumptech.glide.Glide
//import com.google.android.gms.tasks.OnFailureListener
//import com.google.android.gms.tasks.OnSuccessListener
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.UserProfileChangeRequest
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.StorageReference
//import com.google.firebase.storage.UploadTask
//import java.io.IOException
//
//
class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        var items = ArrayList<Information>()

//        val sfd = SimpleDateFormat("dd-MM-yyyy HH:mm")
//        val str = sfd.format(Date(1596826053965))
//
//        Log.e("Date", str)

        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email
        val encEmail = email?.replace("""[.#$]""".toRegex(), ",")

        val rf = FirebaseDatabase.getInstance().reference

        val query = rf.child(encEmail!!).child("orders").orderByChild("time")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){

                    for (d in dataSnapshot.children) {
                        var info: Information? = d.getValue(Information::class.java)
                        if (info != null) {
                            items.add(info)
                            Log.e("Check", info.restro)
                        }
                    }

                    items.reverse()
                    val orderAdapter = OrderAdapter(applicationContext, R.layout.order_record, items)
                    orderListView.adapter = orderAdapter
                } else {
                    emptyOrder.text = "Make your first order!"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                Log.e("Test","Nothing")
//
////                val userName: String? = dataSnapshot.child("Name").getValue(String::class.java)
////                Log.e("Test", userName)
//                var ordFlag = dataSnapshot.child("orders").exists()
//                if (!ordFlag) {
//                    Log.e("Test","Test")
//                    emptyOrder.setText("Make your first order!")
//                } else {
//                    val orders = dataSnapshot.child("orders").children
//
//                    for(d in orders) {
//                        var info: Information? = d.getValue(Information::class.java)
//                        if (info != null) {
//                            items.add(info)
//                        }
//                    }
//
//                    val orderAdapter = OrderAdapter(applicationContext, R.layout.order_record, items)
//                    orderListView.adapter = orderAdapter
//                }
////                orderAdapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                println("The read failed: " + databaseError.code)
//            }
//        })

//        goToMaps.setOnClickListener { //val cartList = cart
//            startActivity(Intent(this, MapsActivity::class.java))
//        }

//        logout.setOnClickListener { //val cartList = cart
//            FirebaseAuth.getInstance().signOut()
//            Toast.makeText(applicationContext, "Logged Out", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this, MainActivity::class.java))
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.orderNow -> {
                startActivity(Intent(this, MapsActivity::class.java))
            }

            R.id.logoutNow -> {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(applicationContext, "Logged Out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            }
            else ->
                return super.onOptionsItemSelected(item)
        }
        return true
    }

}
//        mAuth = FirebaseAuth.getInstance()
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        editText = findViewById<View>(R.id.editTextDisplayName) as EditText
//        imageView = findViewById<View>(R.id.imageView) as ImageView
//        progressBar = findViewById<View>(R.id.progressbar) as ProgressBar
//        textView = findViewById<View>(R.id.textViewVerified) as TextView
//        imageView!!.setOnClickListener { showImageChooser() }
//        loadUserInformation()
//        findViewById<View>(R.id.buttonSave).setOnClickListener { saveUserInformation() }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        if (mAuth!!.currentUser == null) {
//            finish()
//            startActivity(Intent(this, MainActivity::class.java))
//        }
//    }
//
//    private fun loadUserInformation() {
//        val user = mAuth!!.currentUser
//        if (user != null) {
//            if (user.photoUrl != null) {
//                Glide.with(this)
//                    .load(user.photoUrl.toString())
//                    .into(imageView)
//            }
//            if (user.displayName != null) {
//                editText!!.setText(user.displayName)
//            }
//            if (user.isEmailVerified) {
//                textView!!.text = "Email Verified"
//            } else {
//                textView!!.text = "Email Not Verified (Click to Verify)"
//                textView!!.setOnClickListener {
//                    user.sendEmailVerification()
//                        .addOnCompleteListener {
//                            Toast.makeText(
//                                this@ProfileActivity,
//                                "Verification Email Sent",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                }
//            }
//        }
//    }
//
//    private fun saveUserInformation() {
//        val displayName = editText!!.text.toString()
//        if (displayName.isEmpty()) {
//            editText!!.error = "Name required"
//            editText!!.requestFocus()
//            return
//        }
//        val user = mAuth!!.currentUser
//        if (user != null && profileImageUrl != null) {
//            val profile = UserProfileChangeRequest.Builder()
//                .setDisplayName(displayName)
//                .setPhotoUri(Uri.parse(profileImageUrl))
//                .build()
//            user.updateProfile(profile)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Toast.makeText(
//                            this@ProfileActivity,
//                            "Profile Updated",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//        }
//    }
//
//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == CHOOSE_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
//            uriProfileImage = data.data
//            try {
//                val bitmap =
//                    MediaStore.Images.Media.getBitmap(contentResolver, uriProfileImage)
//                imageView!!.setImageBitmap(bitmap)
//                uploadImageToFirebaseStorage()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    private fun uploadImageToFirebaseStorage() {
//        val profileImageRef: StorageReference = FirebaseStorage.getInstance()
//            .getReference("profilepics/" + System.currentTimeMillis() + ".jpg")
//        if (uriProfileImage != null) {
//            progressBar!!.visibility = View.VISIBLE
//            profileImageRef.putFile(uriProfileImage)
//                .addOnSuccessListener(OnSuccessListener<Any> { taskSnapshot ->
//                    progressBar!!.visibility = View.GONE
//                    profileImageUrl = taskSnapshot.getDownloadUrl().toString()
//                })
//                .addOnFailureListener(OnFailureListener { e ->
//                    progressBar!!.visibility = View.GONE
//                    Toast.makeText(this@ProfileActivity, e.message, Toast.LENGTH_SHORT).show()
//                })
//        }
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.menuLogout -> {
//                FirebaseAuth.getInstance().signOut()
//                finish()
//                startActivity(Intent(this, MainActivity::class.java))
//            }
//        }
//        return true
//    }
//
//    private fun showImageChooser() {
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(
//            Intent.createChooser(intent, "Select Profile Image"),
//            CHOOSE_IMAGE
//        )
//    }
//
//    companion object {
//        private const val CHOOSE_IMAGE = 101
//    }
//}