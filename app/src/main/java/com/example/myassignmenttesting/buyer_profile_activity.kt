package com.example.myassignmenttesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class buyer_profile_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buyer_profile)

        val connectChatRoom = findViewById<Button>(R.id.button)
        connectChatRoom.setOnClickListener{
            val Intent = Intent(this, buyer_chatroom_activity::class.java)
            startActivity(Intent)
        }

        val setting = findViewById<Button>(R.id.button1)
        setting.setOnClickListener{
            val Intent = Intent(this, edit_profile_activity::class.java)
            startActivity(Intent)
        }

        val connectSellerCenter = findViewById<Button>(R.id.seller_center)
        connectSellerCenter.setOnClickListener{
            val Intent = Intent(this, seller_dashboard_activity::class.java)
            startActivity(Intent)
        }
    }
}