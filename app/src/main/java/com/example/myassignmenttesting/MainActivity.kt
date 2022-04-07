package com.example.myassignmenttesting

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myassignmenttesting.databinding.ActivityMainBinding
import com.example.myassignmenttesting.ui.main.MainFragment
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db : FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()


//        binding.submit.setOnClickListener {
//                @Override
//                public void onClick(View view){
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
//                    builder.setCancelable(true)
//                    builder.setTitle("This is title")
//                    builder.setMessage("This is message")
//                    builder.setnavigationButton("Cancel", new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i ){
//                            binding.alert.visibility = VISIBLE
//                        }
//                    })
//                    bu
//
//                }
//        }


//
//            val builder = AlertDialog.Builder(this)
//            //set title for alert dialog
//            builder.setTitle(R.string.dialogTitle)
//            //set message for alert dialog
//            builder.setMessage(R.string.dialogMessage)
//            builder.setIcon(android.R.drawable.ic_dialog_alert)
//
//            //performing positive action
//            builder.setPositiveButton("Yes"){dialogInterface, which ->
//                Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
//            }
//            //performing cancel action
//            builder.setNeutralButton("Cancel"){dialogInterface , which ->
//                Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
//            }
//            //performing negative action
//            builder.setNegativeButton("No"){dialogInterface, which ->
//                Toast.makeText(applicationContext,"clicked No",Toast.LENGTH_LONG).show()
//            }
//            // Create the AlertDialog
//            val alertDialog: AlertDialog = builder.create()
//            // Set other dialog properties
//            alertDialog.setCancelable(false)
//            alertDialog.show()


        binding.submit.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val address = binding.address.text.toString()
            var gender = ""
            if (binding.female.isChecked && binding.male.isChecked) {
                gender =""
            } else if (binding.male.isChecked) {
                gender = "Male"
            }
            else if (binding.female.isChecked ){
                gender = "Female"
            }
            else{
                gender = ""
            }
            val age = binding.age.inputType


            if(email!="" && password!="" && address!="" && age>=18 && gender!= "") {
                //val user = new User

                val user = User(email, password, address, gender, age)

                db.collection("User").document("$email").set(user)

                val intent = Intent(this, MainActivity2::class.java)

                intent.putExtra("email", email)
                startActivity(intent)
            }

            else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}