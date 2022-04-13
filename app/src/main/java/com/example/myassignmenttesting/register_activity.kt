package com.example.myassignmenttesting

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.myassignmenttesting.databinding.ActivityMainBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class register_activity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db : FirebaseFirestore
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var mDbRef : DatabaseReference

    var email = ""
    var username = ""
    var password = ""
    var address = ""
    var age : Int = 0
    var gender = ""

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


        binding.male .setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.female.setChecked(false)
            }
        })
        binding.female.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.male.setChecked(false)
            }
        })

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
                val status = "activated"
                val user = User("",email, username,password,address,gender,age,status)
                addUserToDatabase(username, email, firebaseAuth.currentUser?.uid!!)

//                mDbRef = FirebaseDatabase.getInstance().getReference()
//                var chatUser = UserChat(uid=firebaseAuth.currentUser?.uid!!,email=email,username=username)
//                mDbRef.child("user").child(firebaseAuth.currentUser?.uid!!).setValue(chatUser)

                db.collection("User").document("$email").set(user)

                startActivity(Intent(this, BuyerLoginActivity::class.java))
                finish()
            }

            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Email already exist"  , Toast.LENGTH_SHORT).show()
            }
    }

    private fun addUserToDatabase(username: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        var chatUser = UserChat(uid=uid,email=email,username=username)
        mDbRef.child("user").child(uid).setValue(chatUser)
    }

    override fun onBackPressed() {
        // code here to show dialog
        super.onBackPressed() // optional depending on your needs
    }
}