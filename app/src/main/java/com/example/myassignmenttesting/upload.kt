package com.example.myassignmenttesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_upload.*

class upload : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_upload)

        btnViewAll.setOnClickListener {
            startActivity(Intent(this,myProduct::class.java))
        }
        btnAddInfo.setOnClickListener {
            startActivity(Intent(this, UploadActivity::class.java))
        }

    }



}