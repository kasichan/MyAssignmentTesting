package com.example.myassignmenttesting


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myassignmenttesting.databinding.ActivityMainBinding
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
            if (binding.female.isChecked && binding.male.isChecked) {
                gender =""
            } else if (binding.male.isChecked) {
                gender = "Male"
            }
            else if (binding.female.isChecked ){
                gender = "Female"
            }
            else{
                gender = ""
            }
            val age = binding.age.inputType


            if(email!="" && password!="" && address!="" && age>=18 && gender!= "") {
                //val user = new User

                val user = User(email, password, address, gender, age)

                db.collection("User").document("$email").set(user)

                val intent = Intent(this, MainActivity2::class.java)

                intent.putExtra("email", email)
                startActivity(intent)
            }

            else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}