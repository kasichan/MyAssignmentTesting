package com.example.myassignmenttesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class buyer_profile_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buyer_profile)

        val connectChatroom = findViewById<Button>(R.id.chat_button)
        connectChatroom.setOnClickListener {
            val Intent = Intent(this, buyer_chatroom_activity::class.java)
            startActivity(Intent)
        }

    }
}