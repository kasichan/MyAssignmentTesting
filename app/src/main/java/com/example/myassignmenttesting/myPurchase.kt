package com.example.myassignmenttesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.my_purchase.*

class myPurchase : AppCompatActivity() {


    private lateinit var db: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_purchase)

        val firebaseUser = firebaseAuth.currentUser
        val userEmail = firebaseUser!!.email.toString()

        db.collection("Order").whereEqualTo("buyerEmail", userEmail)
            .get().addOnSuccessListener {
                for (document in it) {
                    ProductName.text= document.get("name").toString()
                    buyQuantity.text=document.get("buyQuantity").toString()
                    totalPrice.text=document.get("totalPrice").toString()
                    paymentMethod.text=document.get("paymentMethod").toString()
                    receiverAddress.text=document.get("receiverAddress").toString()
                    senderAddress.text=document.get("senderAddress").toString()
                    senderEmail.text=document.get("sellerEmail").toString()
                    receiverEmail.text=document.get("buyerEmail").toString()

                }
            }

        viewShipment_btn.setOnClickListener {
            val mIntent = Intent(this, Maps_Activity::class.java)

            mIntent.putExtra("senderEmail", senderEmail.text.toString())
            mIntent.putExtra("senderAddress", senderAddress.text.toString())
            startActivity(mIntent)
        }
    }
}