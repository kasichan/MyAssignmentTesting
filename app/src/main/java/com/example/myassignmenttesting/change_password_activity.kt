package com.example.myassignmenttesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myassignmenttesting.databinding.ChangePasswordBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class change_password_activity : AppCompatActivity() {

    private lateinit var binding:ChangePasswordBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var db: FirebaseFirestore

    var oldPassword1 = ""
    var newPassword = ""
    var confirmPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.confirmChange.setOnClickListener {
            val firebaseUser = firebaseAuth.currentUser

            if(firebaseUser!=null){
                val email = firebaseUser.email.toString()


                oldPassword1 = binding.oldPassword.text.toString()
                newPassword = binding.newPassword.text.toString()
                confirmPassword = binding.changeConfirmPassword.text.toString()

                db.collection("User").whereEqualTo("email", email).get().addOnSuccessListener {
                    for (document in it)
                    {
                        if(oldPassword1.equals(document.get("password").toString())){
                            if(newPassword.length<6){
                              binding.newPassword.error = "Password must at least 6 characters"
                            }
                            else if(confirmPassword != newPassword){
                                binding.changeConfirmPassword.error = "Confirm password not match"
                            }
                            else{
                                val username = document.get("username").toString()
                                val address = document.get("address").toString()
                                val gender = document.get("gender").toString()
                                val age = Integer.parseInt(document.get("age").toString())
                                val status = document.get("status").toString()
                                val user = User("",email, username,newPassword,address,gender,age,status)
                                db.collection("User").document(email).set(user).addOnSuccessListener {

                                    firebaseUser!!.updatePassword(newPassword)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(this, "Your profile has been updated"  , Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                }
                            }
                        }
                        else{
                            Toast.makeText(this, "Old password not match ${document.get("password")}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } //if no user logged In
            else{
                startActivity(Intent(this, BuyerLoginActivity::class.java))
                finish()
            }
        }
    }
}