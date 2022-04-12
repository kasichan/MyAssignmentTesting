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
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {


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
        var sellerEmail = intss.getStringExtra("SELLEREMAIL")

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
        editProduct_btn.setOnClickListener {

            val mIntent = Intent(this, new_edit_product::class.java)

            mIntent.putExtra("NAMET", nameT)
            mIntent.putExtra("DESCRIT", desT)
            mIntent.putExtra("IMGURI", imgT)
            mIntent.putExtra("PRICET", priceT)
            mIntent.putExtra("CATT", catT)
            mIntent.putExtra("QUANT", quanT)
            mIntent.putExtra("IMGNAMET", imageNameT)
            mIntent.putExtra("SELLEREMAILT", sellerEmail)
            startActivity(mIntent)

        }

    }


}