package com.example.myassignmenttesting

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.buy_product.*
import java.io.File

class buyProduct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_product)


        val intss = intent
        var nameT = intss.getStringExtra("NAMET")
        var desT = intss.getStringExtra("DESCRIT")
        var imgT = intss.getStringExtra("IMGURI")
        var priceT = intss.getStringExtra("PRICET")
        var quanT = intss.getStringExtra("QUANT")
        var imageNameT = intss.getStringExtra("IMGNAMET")
        var realPrice = priceT?.toDouble()

        val storageRef = FirebaseStorage.getInstance().reference.child("Product_images/$imgT")
        val localfile = File.createTempFile("tempImage", "jpeg")
        storageRef.getFile(localfile)
            .addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                buyImage.setImageBitmap(bitmap)


                buyName.text = nameT
                buyDescription.text = desT
                buyPrice.text = priceT
                buyTotalPrice.text = (realPrice!! * 1).toString()


            }

        var qty = buyQuan.text
        buyQuan.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if( buyQuan.text.toString().isEmpty()){
                    buyTotalPrice.text = "0.0"
                }

                else if(buyQuan.text.toString().isNotEmpty()){
                    if(Integer.parseInt(qty.toString())>Integer.parseInt(quanT.toString())){
                        buyQuan.error = "The quantity cannot be more than ${quanT.toString()}"
                    }
                    else{
                        val quantaDole = buyQuan.text.toString().toDouble()
                        buyTotalPrice.text = (realPrice!! * quantaDole).toString()
                    }

                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {



            }
        })

        buyProduct_btn.setOnClickListener {

            val mIntent = Intent(this, edit_product::class.java)

            mIntent.putExtra("NAMET", nameT)
            mIntent.putExtra("DESCRIT", desT)
            mIntent.putExtra("IMGURI", imgT)
            mIntent.putExtra("PRICET", priceT)
            mIntent.putExtra("IMGNAMET", imageNameT)
            startActivity(mIntent)

        }
    }
}