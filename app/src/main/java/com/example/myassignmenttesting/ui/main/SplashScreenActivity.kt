package com.example.myassignmenttesting.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.myassignmenttesting.MainActivity
import com.example.myassignmenttesting.R


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val shopBag = findViewById<ImageView>(R.id.shop_bag)
        val logoName = findViewById<TextView>(R.id.logoName)

        shopBag.alpha = 0f
        shopBag.animate().setDuration(1500).alpha(1f).withEndAction{
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

//        logoName.alpha = 0f
//        logoName.animate().setDuration(1500).alpha(1f).withEndAction{
//            val i = Intent(this,MainActivity::class.java)
//            startActivity(i)
//            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
//            finish()
//        }
    }
}