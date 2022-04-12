package com.example.myassignmenttesting

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.example.myassignmenttesting.model.Product
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity() {

    private var mImageUri :Uri? = null
    private var mStorageRef:StorageReference? = null
    private var mDatabaseRef:DatabaseReference? = null
    private var mUploadTask: StorageTask<*>? = null
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

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
        }
        /**set data*/

        mStorageRef = FirebaseStorage.getInstance().getReference("Product_images")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Product_images")

        button_choose_image.setOnClickListener { openFileChoose() }
        upLoadBtn.setOnClickListener {
            if (mUploadTask != null && mUploadTask!!.isInProgress){
                Toast.makeText(this@UploadActivity,
                    "An Upload is Still in Progress",
                    Toast.LENGTH_SHORT).show()
            }
            if(nameEditText.text.toString().isEmpty()){
                nameEditText.error="Product Name must be included"
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
//            else if(category.selectedItem.toString().trim().isEmpty()){
//                Toast.makeText((this,"Please select a category for product", Toast.LENGTH_SHORT).show())
//            }
            else{
                uploadFile()
            }
        }



    }
    private fun openFileChoose() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data
            chooseImageView.setImageURI(mImageUri)
        }
    }
    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    private fun uploadFile() {

        if (mImageUri != null) {
            val newFileName =System.currentTimeMillis().toString()
            val fileReference = mStorageRef!!.child(
                newFileName + "." + getFileExtension(mImageUri!!)

            )

            progressBar.visibility = View.VISIBLE
            progressBar.isIndeterminate = true
            mUploadTask = fileReference.putFile(mImageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    val handler = Handler()
                    handler.postDelayed({
                        progressBar.visibility = View.VISIBLE
                        progressBar.isIndeterminate = false
                        progressBar.progress = 0
                    }, 500)
                    Toast.makeText(
                        this@UploadActivity,
                        "Product data Upload successful",
                        Toast.LENGTH_LONG
                    )
                        .show()

                    if(nameEditText!=null){

                    }

                    val upload = Product(
                        name = nameEditText!!.text.toString().trim { it <= ' ' },
                        imageUrl = newFileName,
                        description =  descriptionEditText!!.text.toString().trim { it <= ' ' },
                        price = priceEditText!!.text.toString().trim { it <= ' ' }.toDouble(),
                        category = category.selectedItem.toString(),
                        quantity = Integer.parseInt(quantityEditText!!.text.toString().trim { it <= ' ' }),
                    )
                    mDatabaseRef!!.child("$newFileName").setValue(upload)
                    progressBar.visibility = View.INVISIBLE
                    openImagesActivity()
                }
                .addOnFailureListener { e ->
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@UploadActivity, e.message, Toast.LENGTH_SHORT).show()
                    Log.e("data","${e.message}")
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress =
                        (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                    progressBar.progress = progress.toInt()
                }
        }

        else {
            Toast.makeText(this, "You haven't Selected Any file selected", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun  openImagesActivity() {
        startActivity(Intent(this@UploadActivity, UploadActivity::class.java))
    }



}