package com.example.myassignmenttesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class buyer_chatroom_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buyer_chatroom)
        val backToProfile = findViewById<ImageButton>(R.id.imageButton)
        backToProfile.setOnClickListener{
            val Intent = Intent(this, buyer_profile_activity::class.java)
            startActivity(Intent)
        }
    }
}