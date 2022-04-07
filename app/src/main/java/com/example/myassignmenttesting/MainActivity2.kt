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
        db.collection("User").whereEqualTo("email",intent.getStringArrayExtra("email")).get()
            .addOnSuccessListener {
            for(document in it){
                binding.textViewEmail.text = document.get("email").toString()
                binding.textViewPassword.text = document.get("password").toString()
            }
        }
            .addOnFailureListener{
                Log.w("TAG","Error in getting document: $it")
            }

    }
}