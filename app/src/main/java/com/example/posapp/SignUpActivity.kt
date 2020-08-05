package com.example.posapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
//import android.support.v7.app.AlertDialog
//import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException


class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    var progressBar: ProgressBar? = null
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText
        progressBar = findViewById<View>(R.id.progressbar) as ProgressBar
        mAuth = FirebaseAuth.getInstance()
        findViewById<View>(R.id.buttonSignUp).setOnClickListener(this)
        findViewById<View>(R.id.textViewLogin).setOnClickListener(this)
    }

    private fun registerUser() {
        val email = editTextEmail!!.text.toString().trim { it <= ' ' }
        val password = editTextPassword!!.text.toString().trim { it <= ' ' }
        if (email.isEmpty()) {
            editTextEmail!!.error = "Email is required"
            editTextEmail!!.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail!!.error = "Please enter a valid email"
            editTextEmail!!.requestFocus()
            return
        }
        if (password.isEmpty()) {
            editTextPassword!!.error = "Password is required"
            editTextPassword!!.requestFocus()
            return
        }
        if (password.length < 6) {
            editTextPassword!!.error = "Minimum length of password should be 6"
            editTextPassword!!.requestFocus()
            return
        }
        progressBar!!.visibility = View.VISIBLE
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar!!.visibility = View.GONE
                if (task.isSuccessful) {
                    finish()
                    Log.e("SignUp", "Successful")
//                    startActivity(Intent(this, ProfileActivity::class.java))
                } else {
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        Toast.makeText(
                            applicationContext,
                            "You are already registered",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonSignUp -> registerUser()
            R.id.textViewLogin -> {
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}