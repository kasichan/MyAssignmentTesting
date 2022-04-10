package com.example.myassignmenttesting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.myassignmenttesting.databinding.FragmentProfileBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var firebaseAuth : FirebaseAuth

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater, R.layout.fragment_profile, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()

        verifyUser()

        binding.setting.setOnClickListener{
                view : View ->
            view.findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            verifyUser()
        }

        return binding.root
    }

    private fun verifyUser(){
        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser!=null) {
            val email = firebaseUser.email.toString()


            db.collection("User").whereEqualTo("email", email).get().addOnSuccessListener {
                for (document in it) {
                    binding.username.setText(document.get("username").toString())
                }
            }
        }
        else
        {
            binding.setting.setOnClickListener{
                    view : View ->
                view.findNavController().navigate(R.id.action_profileFragment_to_userLoginFragment)
            }
        }
    }
}