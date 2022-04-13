package com.example.myassignmenttesting

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.myassignmenttesting.databinding.ActivityMainBinding
import com.example.myassignmenttesting.databinding.SellerDashboardBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class seller_dashboard_activity : AppCompatActivity() {
    private lateinit var binding: SellerDashboardBinding
    private lateinit var db : FirebaseFirestore
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SellerDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()

        verifyUser()

//        binding.chat3.setOnClickListener {
//            startActivity(Intent(this,buy_chat_layout_recycle_activity::class.java))
//        }

        binding.addProduct.setOnClickListener {
            startActivity(Intent(this,UploadActivity::class.java))
        }

        binding.myProduct.setOnClickListener {
            startActivity(Intent(this,myProduct::class.java))
        }

        binding.shipping.setOnClickListener {
            startActivity(Intent(this,myPurchase::class.java))
        }


    }

    private fun verifyUser(){
        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser!=null) {
            val email = firebaseUser.email.toString()


            db.collection("User").whereEqualTo("email", email).get().addOnSuccessListener {
                for (document in it) {
                    binding.sellerName.text = document.get("username").toString()
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