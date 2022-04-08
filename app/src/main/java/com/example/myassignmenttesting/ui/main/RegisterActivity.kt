package com.example.myassignmenttesting.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myassignmenttesting.databinding.RegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: RegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}