package com.example.myassignmenttesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.myassignmenttesting.databinding.BuyerProfileBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class buyer_profile_activity : AppCompatActivity() {

    private lateinit var binding: BuyerProfileBinding

    private lateinit var actionBar: ActionBar

    private lateinit var firebaseAuth : FirebaseAuth

    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BuyerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        firebaseAuth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()

        verifyUser()

        binding.logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            verifyUser()
        }
    }

    private fun verifyUser(){
        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser!=null) {
            val email = firebaseUser.email.toString()


            db.collection("User").whereEqualTo("email", email).get().addOnSuccessListener {
                for (document in it) {
                    binding.username.setText(document.get("username").toString())
                }
            }


        }
        else
        {
            startActivity(Intent(this, BuyerLoginActivity::class.java))
            finish()
        }
    }
}