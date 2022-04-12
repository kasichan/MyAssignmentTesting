package com.example.myassignmenttesting

import android.app.Dialog
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_edit_product.*
import kotlinx.android.synthetic.main.activity_edit_product.category
import java.io.File

class edit_product : AppCompatActivity() {


    var reference: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var database: DatabaseReference

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)

        val intss = intent
        var nameT = intss.getStringExtra("NAMET")
        var desT = intss.getStringExtra("DESCRIT")
        var imgT = intss.getStringExtra("IMGURI")
        var priceT = intss.getStringExtra("PRICET")
        var catT = intss.getStringExtra("CATT")
        var quanT = intss.getStringExtra("QUANT")
        var imageNameT = intss.getStringExtra("IMGNAMET")


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
                editcatTextView.setText(catT)
                editquanTextView.setText(quanT)
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

        edit_btn.setOnClickListener {
            editFile()
        }

    }

    private fun editFile() {

//
//        auth = FirebaseAuth.getInstance()
//        databaseReference = FirebaseDatabase.getInstance().getReference("Product_images")
//
//        val prodNewName = editnameDetailTextView!!.text.toString().trim { it <= ' ' }
//        val prodNewDescription = editdescriptionDetailTextView!!.text.toString().trim { it <= ' ' }
//        val prodNewPrice = editpriceTextView.text.toString().trim { it <= ' ' }.toDouble()
//        val prodNewCat = editcatTextView.text.toString()
//        val prodNewQuan = Integer.parseInt(editquanTextView!!.text.toString().trim { it <= ' ' })
//        val prodImageName = imageOriName
//        val product = Product(prodNewName,prodImageName,prodNewDescription,prodNewPrice,prodNewCat,prodNewQuan)
//
//        if(uid != null){
//            databaseReference.child(uid).setValue(product).addOnCompleteListener{
//                if(it.isSuccessful){
//                    Toast.makeText(this,"Success to update product", Toast.LENGTH_SHORT).show()
//                }else{
//                    Toast.makeText(this,"Failed to update product", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }


//        lateinit var database: DatabaseReference
//
//                    val upload = Product(
//                        name = editnameDetailTextView!!.text.toString().trim { it <= ' ' },
//                        imageUrl = fileName,
//                        description =  editdescriptionDetailTextView!!.text.toString().trim { it <= ' ' },
//                        price = editpriceTextView!!.text.toString().trim { it <= ' ' }.toDouble(),
//                        category = category.selectedItem.toString(),
//                        quantity = Integer.parseInt(editcatTextView!!.text.toString().trim { it <= ' ' }),
//                    )
//        database.child("Product_images").child()






        }



}