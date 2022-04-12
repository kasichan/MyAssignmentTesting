package com.example.myassignmenttesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.myassignmenttesting.databinding.EditProfileBinding
import com.example.myassignmenttesting.databinding.EditProductBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class edit_profile_activity : AppCompatActivity() {


    private lateinit var binding: EditProfileBinding

    private lateinit var actionBar: ActionBar

    private lateinit var firebaseAuth : FirebaseAuth

    private lateinit var db: FirebaseFirestore

    //var email = ""
    var username = ""
    var password = ""
    var confirmPassword = ""
    var address = ""
    var gender = ""
    var age : Int = 0
    var status = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar = supportActionBar!!
        actionBar.title = "Edit Profile"
        actionBar.setDisplayHomeAsUpEnabled(true)


        binding = EditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        verifyUser()

        binding.editMale.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.editFemale.setChecked(false)
            }
        })
        binding.editFemale.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.editMale.setChecked(false)
            }
        })

        binding.update.setOnClickListener {
            validateUpdate()
        }



    }

    private fun update(){
        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser!=null) {
            val currentEmail = firebaseUser.email.toString()
            val user = User("",currentEmail, username,password,address,gender,age,status)
            db.collection("User").document("$currentEmail").set(user).addOnSuccessListener {



                            firebaseUser!!.updatePassword(password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(this, "Your profile has been updated"  , Toast.LENGTH_SHORT).show()
                                    }
                                }

                    }

            }
        }




    private fun validateUpdate(){

        //email = binding.editEmail.text.toString()
        username = binding.editUsername.text.toString()
        password = binding.editPassword.text.toString()
        confirmPassword = binding.confirmPassword.text.toString()
        address = binding.editAddress.text.toString()


        if(binding.editAge.text.toString()==""){
            age = 0
        }else{
            age = Integer.parseInt(binding.editAge.text.toString())
        }

        if(binding.editFemale.isChecked && binding.editMale.isChecked){
            gender = ""
        }
        else if(binding.editFemale.isChecked){
            gender = "Female"
        }
        else if(binding.editMale.isChecked){
            gender = "Male"
        }
        else{
            gender = ""
        }



        if(TextUtils.isEmpty(username)){
            binding.editUsername.error = "Please enter username"
        }
        else if(age<18){
            binding.editAge.error = "Invalid age"
        }
        else if(!binding.editFemale.isChecked && !binding.editMale.isChecked){
            binding.editGender.error = "Please select gender"
        }
//        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            binding.editEmail.error = "Invalid email format"
//        }
        else if(TextUtils.isEmpty(address)){
            binding.editAddress.error = "Please enter address"
        }
        else if(TextUtils.isEmpty(password)){
            binding.editPassword.error = "Please enter password"
        }
        else if(password.length<6){
            binding.editPassword.error = "Password must at least 6 characters"
        }
        else if(confirmPassword != password) {
            binding.confirmPassword.error = "Password not match"
        }
        else{
            update()
        }
    }

    private fun verifyUser(){

        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser!=null) {
            val email = firebaseUser.email.toString()


            db.collection("User").whereEqualTo("email", email).get().addOnSuccessListener {
                for (document in it) {
                    binding.editUsername.setText(document.get("username").toString())
                    status = document.get("status").toString()

                    if(document.get("gender").toString() == "Male"){
                        binding.editMale.isChecked = true
                    }
                    else{
                        binding.editFemale.isChecked = true
                    }

                    binding.editAge.setText(document.get("age").toString())
                    //binding.editEmail.setText(document.get("email").toString())
                    binding.editAddress.setText(document.get("address").toString())
                    binding.editPassword.setText(document.get("password").toString())


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





