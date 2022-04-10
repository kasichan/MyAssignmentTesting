package com.example.myassignmenttesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class buyer_homepage_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buyer_homepage)

        val connectChatRoom = findViewById<Button>(R.id.chat)
        connectChatRoom.setOnClickListener{
            val Intent = Intent(this, buyer_chatroom_activity::class.java)
            startActivity(Intent)
        }

        val connectSellerDashboard = findViewById<Button>(R.id.search_button)
        connectSellerDashboard.setOnClickListener{
            val Intent = Intent(this, buyer_search_activity::class.java)
            startActivity(Intent)
        }
    }
}