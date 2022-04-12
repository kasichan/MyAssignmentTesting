package com.example.myassignmenttesting.ui.main

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.myassignmenttesting.*

import com.example.myassignmenttesting.databinding.BuyerUserLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class BuyerLoginActivity() : AppCompatActivity() {

    private lateinit var binding: BuyerUserLoginBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog

    private lateinit var db: FirebaseFirestore

    private var email = ""
    private var password = ""
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BuyerUserLoginBinding.inflate(layoutInflater)


        setContentView(binding.root)
//
//        actionBar = supportActionBar!!
//        actionBar.title = "Login"

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        verifyUser()

        binding.loginButton.setOnClickListener {
            validateLoginDetails()
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, register_activity::class.java))
            finish()
        }
    }

    private fun validateLoginDetails() {
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //invalid email format
            binding.email.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            binding.password.error = "Please enter password"
        } else {
            //all ok
            login()
        }


    }

    private fun login() {
        progressDialog.show()

        db.collection("User").whereEqualTo("email", email).whereEqualTo("status", "deactivated")
            .get().addOnSuccessListener {
            for (document in it) {
                Toast.makeText(
                    this,
                    "Your account has been deactivated",
                    Toast.LENGTH_SHORT
                ).show()

//                startActivity(Intent(this,BuyerLoginActivity::class.java))
//                finish()

            }

        }




        db.collection("User").whereEqualTo("email", email).whereEqualTo("status", "activated")
            .get().addOnSuccessListener {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        val firebaseUser = firebaseAuth.currentUser
                        val email = firebaseUser!!.email
                        var username = ""
                        db.collection("User").whereEqualTo("email", email)
                            .whereEqualTo("password", password)
                            .get().addOnSuccessListener {

                                for (document in it) {
                                    Toast.makeText(
                                        this,
                                        "Welcome ${document.get("username")}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    username = document.get("username").toString()
                                }
                                var i = Intent(this, buyer_profile_activity::class.java)
                                i.putExtra("username", username)
                                startActivity(Intent(this, navigation_drawer_activity::class.java))
                                finish()
                            }


                    }

                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    }


            }


    }

    private fun verifyUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this, upload::class.java))
            finish()
        }
    }
}





