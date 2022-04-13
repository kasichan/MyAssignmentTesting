package com.example.myassignmenttesting

import Order
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.myassignmenttesting.model.Product
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import kotlinx.android.synthetic.main.activity_create_payment.*

class createPayment : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private var mDatabaseRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_payment)

        val intss = intent
        var nameT = intss.getStringExtra("NAMET")
        var desT = intss.getStringExtra("DESCRIT")
        var priceT = intss.getStringExtra("PRICET")
        var purchaseQuanT = intss.getStringExtra("PURQUANT")
        var purtotalT = intss.getStringExtra("PURTOTALT")
        var imageNameT = intss.getStringExtra("IMGNAMET")
        var sellerEmail = intss.getStringExtra("SELLEREMAILT")
        var buyerEmail = ""
        var buyerAddress = ""
        var senderAddress1 =""

        Toast.makeText(
            this,
            sellerEmail ,
            Toast.LENGTH_SHORT
        ).show()

        priceTextView.setText("RM "+purtotalT)
        onlinebanking.isChecked = true

        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser!=null) {
            buyerEmail = firebaseUser.email.toString()

            db.collection("User").whereEqualTo("email", buyerEmail).get().addOnSuccessListener {
                for (document in it) {
                    buyerAddress = document.get("address").toString()
                    receiverAddress.setText(document.get("address").toString())
                }
            }


        }
        else
        {

            startActivity(Intent(this, BuyerLoginActivity::class.java))
            finish()
        }


        db.collection("User").whereEqualTo("email", sellerEmail).get().addOnSuccessListener {
            for (document in it) {
                senderAddress1 = document.get("address").toString()
                senderAddress.setText(document.get("address").toString())
            }
        }

        onlinebanking.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                creditdebitcard.isChecked = false

            }
        }
        creditdebitcard.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                onlinebanking.isChecked = false

            }
        }

        order_btn.setOnClickListener {
            var payMethod = ""

            if(onlinebanking.isChecked){
                payMethod = "Online Banking"
            }
            if(creditdebitcard.isChecked){
                payMethod = "Credit / Debit Card"
            }
            val order = Order(
                name = nameT,
                buyerEmail = buyerEmail,
                senderAddress =  senderAddress1,
                receiverAddress = buyerAddress,
                totalPrice = purtotalT?.toDouble(),
                paymentMethod = payMethod,
                buyQuantity = Integer.parseInt(purchaseQuanT),
                sellerEmail = sellerEmail
            )

            val newOrderCode = "SEC"+System.currentTimeMillis().toString()

            db.collection("Order").document("$newOrderCode").set(order).addOnSuccessListener {
                startActivity(Intent(this,navigation_drawer_activity::class.java))
            }



        }


    }
}