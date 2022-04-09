package com.example.myassignmenttesting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.buyer_profile.*

class LoggedIn : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buyer_profile)

        val sharePref= this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin = sharePref.getString("email","1")
        var status = ""

        if(isLogin=="1")
        {
            var email = intent.getStringExtra("email")

            if(email!=null){
                status = "loggedIn"
                setText(email)
                with(sharePref.edit())
                {
                    putString("Email",email)
                    apply()
                }
            }

        }
        else
        {
            var intent = Intent(this,buyer_user_login_activity::class.java)
            startActivity(intent)
            finish()

        }
    }

    private fun setText(email:String?){
        db = FirebaseFirestore.getInstance()

        if(email!=null){
            db.collection("User").document(email).get()
                .addOnSuccessListener {
                        tasks->
                    username.text = tasks.get("username").toString()


                }
        }

    }

}