package com.example.myassignmenttesting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import com.example.myassignmenttesting.databinding.FragmentProfileBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var firebaseAuth : FirebaseAuth

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = inflater.inflate(R.layout.fragment_profile,container,false)

//        firebaseAuth = FirebaseAuth.getInstance()
//
//        db = FirebaseFirestore.getInstance()

//        verifyUser()

//        binding.setting.setOnClickListener {
//            val intent = Intent(activity, profile_setting_activity::class.java)
//            startActivity(intent)
//
//        }
//
//
//
//        binding.seller_center.setOnClickListener {
//            val intent = Intent(activity, seller_dashboard_activity::class.java)
//            startActivity(intent)
//
//        }

        return binding
    }

//    private fun verifyUser(){
//        val firebaseUser = firebaseAuth.currentUser
//
//        if(firebaseUser!=null) {
//            val email = firebaseUser.email.toString()
//
//
//            db.collection("User").whereEqualTo("email", email).get().addOnSuccessListener {
//                for (document in it) {
//                    binding.username.setText(document.get("username").toString())
//                }
//            }
//
//
//        }
//        else
//        {
//            val intent = Intent(activity, BuyerLoginActivity::class.java)
//            startActivity(intent)
//
//        }
//    }

}