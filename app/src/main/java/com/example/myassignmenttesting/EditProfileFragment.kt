package com.example.myassignmenttesting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.myassignmenttesting.databinding.EditProfileBinding
import com.example.myassignmenttesting.databinding.FragmentEditProfileBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.view.*
import kotlinx.android.synthetic.main.fragment_home_.view.*


class EditProfileFragment : Fragment() {

    private lateinit var bind: FragmentEditProfileBinding


    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var db: FirebaseFirestore

    //var email = ""
    var username = ""
    var password = ""
    var confirmPassword2 = ""
    var address = ""
    var gender = ""
    var age: Int = 0
    var status = ""

    private lateinit var actionBar: ActionBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        actionBar = (activity as AppCompatActivity?)!!.supportActionBar!!
        actionBar.title = "Edit Profile"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


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
           Toast.makeText(activity, "oiii", Toast.LENGTH_SHORT).show()
        }



        return binding
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
                            Toast.makeText(activity, "Your profile has been updated"  , Toast.LENGTH_SHORT).show()
                        }
                    }

            }

        }
    }




    private fun validateUpdate(){

        //email = binding.editEmail.text.toString()
        username = editUsername.text.toString()
        password = editPassword.text.toString()
        confirmPassword2 = confirmPassword.text.toString()
        address = editAddress.text.toString()


        if(editAge.text.toString()==""){
            age = 0
        }else{
            age = Integer.parseInt(editAge.text.toString())
        }

        if(editFemale.isChecked && editMale.isChecked){
            gender = ""
        }
        else if(editFemale.isChecked){
            gender = "Female"
        }
        else if(editMale.isChecked){
            gender = "Male"
        }
        else{
            gender = ""
        }



        if(TextUtils.isEmpty(username)){
            editUsername.error = "Please enter username"
        }
        else if(age<18){
            editAge.error = "Invalid age"
        }
        else if(!editFemale.isChecked && !editMale.isChecked){
            editGender.error = "Please select gender"
        }
        else if(TextUtils.isEmpty(address)){
            editAddress.error = "Please enter address"
        }
        else if(TextUtils.isEmpty(password)){
            editPassword.error = "Please enter password"
        }
        else if(password.length<6){
            editPassword.error = "Password must at least 6 characters"
        }
        else if(TextUtils.isEmpty(password)){
            confirmPassword.error = "Please enter password"
        }
        else if(confirmPassword2 != password) {
           confirmPassword.error = "Password not match"
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

                  editUsername.setText(document.get("username").toString())
                    status = document.get("status").toString()

                    if(document.get("gender").toString() == "Male"){
                      editMale.isChecked = true
                    }
                    else{
                    editFemale.isChecked = true
                    }

                 editAge.setText(document.get("age").toString())
                editAddress.setText(document.get("address").toString())
                    editPassword.setText(document.get("password").toString())


                }
            }


        }
        else
       {
            val intent = Intent(activity, BuyerLoginActivity::class.java)
            startActivity(intent)

        }
    }


}