package com.example.myassignmenttesting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class LoggedIn : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buyer_homepage)
        val sharePref= this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin = sharePref.getString("Email","1")
        //val email = intent.getStringExtra("email")

        if(isLogin=="1"){
            with(sharePref.edit())
            {
                putString("Email",email)
                apply()
            }
        }
        else{
//            var intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//            finish()
            setText(isLogin)
        }

    }

    private fun setText(email:String){
        db = FirebaseFirestore.getInstance()
        db.collection("User").document(email).get()
            .addOnSuccessListener {
                tasks->
            }
    }
}