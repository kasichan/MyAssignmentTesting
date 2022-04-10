package com.example.myassignmenttesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_details.*
import android.graphics.BitmapFactory
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.File



class DetailsActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        val intss = intent
        var nameT = intss.getStringExtra("NAMET")
        var desT = intss.getStringExtra("DESCRIT")
        var imgT = intss.getStringExtra("IMGURI")
        var priceT = intss.getStringExtra("PRICET")
        var catT = intss.getStringExtra("CATT")
        var quanT = intss.getStringExtra("QUANT")
        var imageNameT = intss.getStringExtra("IMGNAMET")

        val storageRef = FirebaseStorage.getInstance().reference.child("Product_images/$imgT")
        val localfile = File.createTempFile("tempImage", "jpeg")
        storageRef.getFile(localfile)
            .addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                ProductDetailImageView.setImageBitmap(bitmap)


                nameDetailTextView.text = nameT
                descriptionDetailTextView.text = desT
                priceTextView.text = priceT
                catTextView.text = catT
                quanTextView.text = quanT


            }


        delete_btn.setOnClickListener {
            database = FirebaseDatabase.getInstance().getReference("Product_images")
//            database.child("$imageNameT").removeValue()


            database
                .orderByChild("imageUrl")
                .equalTo("$imageNameT")
                .addListenerForSingleValueEvent(object: ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        dataSnapshot.children.forEach {
                            //"it" is the snapshot
                            val key: String = it.key.toString()
                            database.removeValue()

                        }
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        //do whatever you need
                    }
                })

//                .addOnSuccessListener {
//                    startActivity(Intent(this,ItemsActivity::class.java))
//                    Toast.makeText(this,"Successful remove product",Toast.LENGTH_SHORT).show()
//            }
//                .addOnFailureListener {
//                    Toast.makeText(this,"Fail to remove product",Toast.LENGTH_SHORT).show()
//                }
        }

    }


}