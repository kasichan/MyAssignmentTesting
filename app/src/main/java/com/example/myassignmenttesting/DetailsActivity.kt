package com.example.myassignmenttesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myassignmenttesting.uitel.loadImage
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

        nameDetailTextView.text = nameT
        descriptionDetailTextView.text = desT
        priceTextView.text = priceT
        catTextView.text = catT
        quanTextView.text = quanT
        ProductDetailImageView.loadImage(imgT)


    }
}