package com.example.myassignmenttesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myassignmenttesting.databinding.ProfileSettingsBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.firebase.auth.FirebaseAuth


class profile_setting_activity : AppCompatActivity() {
    private lateinit var binding: ProfileSettingsBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ProfileSettingsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.changePassword.setOnClickListener {
            startActivity(Intent(this,change_password_activity::class.java))
            finish()
        }

        binding.editProfile.setOnClickListener {
            startActivity(Intent(this,edit_profile_activity::class.java))
            finish()
        }

        binding.logout.setOnClickListener {
            logout()
        }

        binding.deactivateAccount.setOnClickListener {
            deactivate()
        }


    }

    private fun logout(){

        firebaseAuth.signOut()
        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser==null) {
            startActivity(Intent(this, BuyerLoginActivity::class.java))
            finish()
        }

    }

    private fun deactivate(){
        val dialog = confirmation_fragment()
        dialog.show(supportFragmentManager,"customDialog")

    }


}