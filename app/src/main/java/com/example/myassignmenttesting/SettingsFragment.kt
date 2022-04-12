package com.example.myassignmenttesting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.myassignmenttesting.databinding.FragmentProfileBinding
import com.example.myassignmenttesting.databinding.FragmentSettingsBinding
import com.example.myassignmenttesting.databinding.ProfileSettingsBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home_.view.*
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.fragment_settings,container,false)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.editProfile.setOnClickListener {
            Navigation.findNavController(binding).navigate(R.id.action_home_Fragment_to_editProfileFragment)
        }

        binding.changePassword.setOnClickListener {
            Navigation.findNavController(binding).navigate(R.id.action_home_Fragment_to_changePasswordFragment)
        }

        binding.contactUs.setOnClickListener {
            Navigation.findNavController(binding).navigate(R.id.action_home_Fragment_to_contactUsFragment)
        }

        binding.deactivateAccount.setOnClickListener {
            deactivate()
        }

        binding.logout.setOnClickListener {
            logout()
        }

        return binding
    }





    private fun logout(){

        firebaseAuth.signOut()
        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser==null) {
            val intent = Intent(activity, BuyerLoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun deactivate(){
        val dialog = confirmation_fragment()
        dialog.show(childFragmentManager,"customDialog")

    }


}