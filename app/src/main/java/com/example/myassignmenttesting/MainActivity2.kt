package com.example.myassignmenttesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myassignmenttesting.databinding.ActivityMain2Binding
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        val email = intent.getStringExtra("email").toString()

        db.collection("User").document("$email").get()
            .addOnSuccessListener {

                binding.textViewEmail.setText(it.get("email").toString())
                binding.textViewPassword.setText(it.get("password").toString())
                binding.textViewAddress.setText(it.get("address").toString())
                binding.textViewGender.setText(it.get("gender").toString())
                binding.textViewAge.setText(it.get("age").toString())

        }
            .addOnFailureListener{
                Log.w("Firestore","Error in getting document: $it")
            }

    }
}