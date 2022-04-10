package com.example.myassignmenttesting

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.myassignmenttesting.databinding.ActivityMainBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.example.myassignmenttesting.ui.main.SplashScreenActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db : FirebaseFirestore
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth : FirebaseAuth
    var email = ""
    var username = ""
    var password = ""
    var address = ""
    var gender = ""
    var age : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Register"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating an account")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()

        binding.submit.setOnClickListener {
            email = binding.email.text.toString()
            username = binding.username.text.toString()
            password = binding.password.text.toString()
            address = binding.address.text.toString()
            //gender = ""
            //age = 0

            if(binding.age.text.toString()==""){
                age = 0
            }else{
                age = Integer.parseInt(binding.age.text.toString())
            }

            if(binding.female.isChecked && binding.male.isChecked){
                gender = ""
            }
            else if(binding.female.isChecked){
                gender = "Female"
            }
            else if(binding.male.isChecked){
                gender = "Male"
            }
            else{
                gender = ""
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.email.error = "Invalid email format"
            }
            else if(TextUtils.isEmpty(username)){
                binding.username.error = "Please enter username"
            }
            else if(TextUtils.isEmpty(password)){
                binding.password.error = "Please enter password"
            }
            else if(password.length<6){
                binding.password.error = "Password must at least 6 characters"
            }
            else if(TextUtils.isEmpty(address)){
                binding.address.error = "Please enter password"
            }
            else if(!binding.female.isChecked && !binding.male.isChecked){
                binding.gender.error = "Please select gender"
            }
            else if(age<18){
                binding.age.error = "Invalid age"
            }
            else{
                register()
            }

//            if(email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && address.isNotEmpty() && gender.isNotEmpty() && age >= 18){
//                //val user = new User
//                val user = User(email, username,password,address,gender,age)
//                db.collection("User").document("$email").set(user)
//
//                val intent = Intent(this, BuyerLoginActivity::class.java)
//
//                //intent.putExtra("email",email)
//                startActivity(intent)
//            }
//            else{
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            }


        }
    }

    private fun register(){
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                //val authEmail = firebaseUser!!.email

                val user = User(email, username,password,address,gender,age)
                db.collection("User").document("$email").set(user)

                Toast.makeText(this, "Account has been created with email $email"  , Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, BuyerLoginActivity::class.java))
                finish()
            }

            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Register Failed"  , Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() //go back to previous activity
        return super.onSupportNavigateUp()
    }
}