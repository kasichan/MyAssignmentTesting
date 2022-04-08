package com.example.myassignmenttesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myassignmenttesting.databinding.ActivityMainBinding
import com.example.myassignmenttesting.ui.main.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        binding.submit.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val address = binding.address.text.toString()
            var gender = ""
            if(binding.female.isChecked){
                gender = "Female"
            }
            else if(binding.male.isChecked){
                gender = "Male"
            }
            val age = Integer.parseInt(binding.age.text.toString())

            //val user = new User
            val user = User(email, password,address,gender,age)

            db.collection("User").document("$email").set(user)

            val intent = Intent(this, MainActivity2::class.java)

            intent.putExtra("email",email)
            startActivity(intent)
        }
    }
}