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
import com.example.myassignmenttesting.MainActivity2
import com.example.myassignmenttesting.databinding.BuyerUserLoginBinding
import com.google.firebase.firestore.FirebaseFirestore


class BuyerLoginActivity() : AppCompatActivity() {

    private lateinit var binding: BuyerUserLoginBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog

    //    private lateinit var db: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private var email = ""
    private var password = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BuyerUserLoginBinding.inflate(layoutInflater)


        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Login"

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)

//        db = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        //verifyUser()

        binding.loginButton.setOnClickListener {
            validateLoginDetails()
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validateLoginDetails() {
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //invalid email format
            binding.email.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)) {
            binding.password.error = "Please enter password"
        }
        else {
            //all ok
            progressDialog.show()
            db.collection("User").whereEqualTo("email", email)
                .whereEqualTo("password", password).get().addOnSuccessListener {
                    progressDialog.dismiss()

                    //val editor: SharedPreferences.Editor = sharedpreferences.edit()
                    //editor.putString("email", email)

                    for(document in it){
                        Toast.makeText(this, "Welcome ${document.get("username")}"  , Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity2::class.java))
                        finish()
                    }

                }

                .addOnFailureListener{
                        progressDialog.dismiss()
                        Toast.makeText(this, "Login in failed", Toast.LENGTH_SHORT).show()
                }

        }
    }

}



