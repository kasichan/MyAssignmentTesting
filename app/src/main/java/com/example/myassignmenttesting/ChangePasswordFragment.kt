package com.example.myassignmenttesting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ChangePasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = inflater.inflate(R.layout.fragment_change_password,container,false)


        // Inflate the layout for this fragment
        return binding
    }


}