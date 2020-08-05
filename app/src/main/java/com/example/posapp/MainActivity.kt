package com.example.posapp

import android.content.Intent
import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(), View.OnClickListener {
    var mAuth: FirebaseAuth? = null
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText
        progressBar = findViewById<View>(R.id.progressbar) as ProgressBar
        findViewById<View>(R.id.textViewSignup).setOnClickListener(this)
        findViewById<View>(R.id.buttonLogin).setOnClickListener(this)
    }

    private fun userLogin() {
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
            editTextPassword!!.error = "Minimum lenght of password should be 6"
            editTextPassword!!.requestFocus()
            return
        }
        progressBar!!.visibility = View.VISIBLE
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar!!.visibility = View.GONE
                if (task.isSuccessful) {
                    finish()
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth!!.currentUser != null) {
            finish()
            startActivity(Intent(this, ::class.java))
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.textViewSignup -> {
                finish()
                startActivity(Intent(this, SignUpActivity::class.java))
            }
            R.id.buttonLogin -> userLogin()
        }
    }
}


//import android.os.Bundle

//
//import android.app.AlertDialog
//import android.content.DialogInterface
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException
//import com.google.android.gms.tasks.Task

//
//
//class MainActivity : AppCompatActivity() {
//    //
////    lateinit var mGoogleSignInClient: GoogleSignInClient
////    private val RC_SIGN_IN = 9001
////
////    var personName = ""
////    var personEmail = ""
////    var firstName = ""
////
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
//}

//
//        val gso =
//            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("959431664768-7q3ifufqrdis11aq9p5trs7l3v76bgin.apps.googleusercontent.com")
//                .requestEmail()
//                .build()
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
//
//        val acct = GoogleSignIn.getLastSignedInAccount(this)
//        if (acct != null) {
//            personName = acct.displayName.toString()
//            Log.e("google", personName)
//            firstName = acct.givenName.toString()
////            Log.e("google",personGivenName)
//            //val personFamilyName = acct.familyName
//            personEmail = acct.email.toString()
//            Log.e("google", personEmail)
//            //val personId = acct.id
//            //val personPhoto: Uri? = acct.photoUrl
//            signedInAs.setText("Welcome back, $firstName!")
//            signedInEmail.setText("You're logged in as $personEmail!")
//        }
//        google_login_btn.setOnClickListener {
//            signIn()
//        }
//
//        logout.setOnClickListener {
//            signOut()
//        }
//    }
//
//    private fun signIn() {
//        val signInIntent = mGoogleSignInClient.signInIntent
//        startActivityForResult(
//            signInIntent, RC_SIGN_IN
//        )
//    }
//
//    private fun signOut() {
//        mGoogleSignInClient.signOut()
//            .addOnCompleteListener(this) {
//                // Update your UI here
//                var msg = "Please log in to an account!"
//                if (personName != "")
//                    msg = "Logged out as $personName!"
//                AlertDialog.Builder(this)
//                    .setTitle("Logout")
//                    .setMessage(msg) // Specifying a listener allows you to take an action before dismissing the dialog.
//                    // The dialog is automatically dismissed when a dialog button is clicked.
//                    .setPositiveButton(
//                        android.R.string.yes,
//                        DialogInterface.OnClickListener { dialog, which ->
//                            // Continue with delete operation
//                        }) // A null listener allows the button to dismiss the dialog and take no further action.
//                    .setNegativeButton(android.R.string.no, null)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show()
//            }
////        personName = ""
//        signedInAs.setText("")
//        signedInEmail.setText("")
//    }
//
//    private fun revokeAccess() {
//        mGoogleSignInClient.revokeAccess()
//            .addOnCompleteListener(this) {
//                // Update your UI here
//            }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == RC_SIGN_IN) {
//            val task =
//                GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//        }
//    }
//
//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(
//                ApiException::class.java
//            )
//            // Signed in successfully
//            val googleId = account?.id ?: ""
//            Log.i("Google ID", googleId)
//
//            val googleFirstName = account?.givenName ?: ""
//            Log.i("Google First Name", googleFirstName)
//
//            val googleLastName = account?.familyName ?: ""
//            Log.i("Google Last Name", googleLastName)
//
//            val googleEmail = account?.email ?: ""
//            Log.i("Google Email", googleEmail)
//
//            val googleProfilePicURL = account?.photoUrl.toString()
//            Log.i("Google Profile Pic URL", googleProfilePicURL)
//
//            val googleIdToken = account?.idToken ?: ""
//            Log.i("Google ID Token", googleIdToken)
//
//
//            val myIntent = Intent(this, MapsActivity::class.java)
//            //myIntent.putExtra("google_id", googleId)
//            myIntent.putExtra("first_name", googleFirstName)
////            myIntent.putExtra("google_last_name", googleLastName)
////            myIntent.putExtra("google_email", googleEmail)
////            myIntent.putExtra("google_profile_pic_url", googleProfilePicURL)
////            myIntent.putExtra("google_id_token", googleIdToken)
//            this.startActivity(myIntent)
//        } catch (e: ApiException) {
//            // Sign in was unsuccessful
//            Log.e(
//                "failed code=", e.statusCode.toString()
//            )
//        }
//    }
//}
