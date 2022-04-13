package com.example.myassignmenttesting

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.example.myassignmenttesting.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.android.synthetic.main.new_edit_product.*
import kotlinx.android.synthetic.main.new_edit_product.category
import java.io.File

class new_edit_product : AppCompatActivity() {

    private lateinit var database: DatabaseReference



    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    var reference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database.reference

        setContentView(R.layout.new_edit_product)
        val intss = intent
        var nameT = intss.getStringExtra("NAMET")
        var desT = intss.getStringExtra("DESCRIT")
        var imgT = intss.getStringExtra("IMGURI")
        var priceT = intss.getStringExtra("PRICET")
        var catT = intss.getStringExtra("CATT")
        var quanT = intss.getStringExtra("QUANT")
        var imageNameT = intss.getStringExtra("IMGNAMET")
        var sellerEmail = intss.getStringExtra("SELLEREMAIL")

        val spinner: Spinner = category
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.category,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            if (catT != null) {
                val spinnerPosition = adapter.getPosition(catT)
                spinner.setSelection(spinnerPosition)
            }
        }

        val storageRef = FirebaseStorage.getInstance().reference.child("Product_images/$imgT")
        val localfile = File.createTempFile("tempImage", "jpeg")
        storageRef.getFile(localfile)
            .addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                editProductDetailImageView.setImageBitmap(bitmap)
                editnameDetailTextView.setText(nameT)
                editdescriptionDetailTextView.setText(desT)
                editpriceTextView.setText(priceT)
                editquanTextView.setText(quanT)
            }



        edit_btn.setOnClickListener {

            if(editnameDetailTextView.text.toString() == null){
                editnameDetailTextView.error="Product Name must be included"
            }
            else if(nameEditText.text.length > 50){
                nameEditText.error="Product Name length cannot more than 50 words"
            }
            else if(nameEditText.text.length <3){
                nameEditText.error="Product Name length must at least 3 words"
            }
            else if(nameEditText.text.isDigitsOnly()){
                nameEditText.error="Product Name must include words"
            }
            else if(descriptionEditText.text.isEmpty()){
                descriptionEditText.error="Product description must be included"
            }
            else if (descriptionEditText.text.length < 50){
                descriptionEditText.error="Product description must have at least 50 words"
            }
            else if (descriptionEditText.text.length > 300){
                descriptionEditText.error="Product description cannot be more than 300 words"
            }
            else if(descriptionEditText.text.isDigitsOnly()){
                descriptionEditText.error="Product description must include words"
            }
            else if(priceEditText.text.isEmpty()){
                priceEditText.error="Product Price must be included"
            }
            else if (quantityEditText.text.isEmpty()){
                quantityEditText.error ="Product Quantity must be included"
            }
            else if (!quantityEditText.text.isDigitsOnly()){
                quantityEditText.error ="Product Quantity can only include digits number"
            }
            else {

                val edit = Product(
                    name = editnameDetailTextView!!.text.toString().trim { it <= ' ' },
                    imageUrl = imageNameT,
                    description = editdescriptionDetailTextView!!.text.toString()
                        .trim { it <= ' ' },
                    price = editpriceTextView!!.text.toString().trim { it <= ' ' }.toDouble(),
                    category = category.selectedItem.toString(),
                    quantity = Integer.parseInt(
                        editquanTextView!!.text.toString().trim { it <= ' ' }),
                    sellerEmail = sellerEmail
                )
                database.child("Product_images").child("$imageNameT").setValue(edit)
                    .addOnSuccessListener {
                        startActivity(Intent(this, ItemsActivity::class.java))
                    }
            }


        }

        delete_btn.setOnClickListener {
            reference = FirebaseDatabase.getInstance().getReference("Product_images")
            reference!!.child("$imageNameT").removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this,"Successful remove product",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Fail to remove product",Toast.LENGTH_SHORT).show()
                }
        }

    }
}