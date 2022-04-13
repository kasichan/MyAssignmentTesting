package com.example.myassignmenttesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myassignmenttesting.adapter.ListAdapterMy
import com.example.myassignmenttesting.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_items.*

class myProduct : AppCompatActivity() {
    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null
    private lateinit var mProducts: MutableList<Product>
    private lateinit var listAdapter: ListAdapterMy

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_product)
        firebaseAuth = FirebaseAuth.getInstance()
        /**set adapter*/
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this@myProduct)
        myDataLoaderProgressBar.visibility = View.VISIBLE
        mProducts = ArrayList()




        val firebaseUser = firebaseAuth.currentUser
        val userEmail = firebaseUser!!.email.toString()
        /**set Firebase Database*/
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Product_images")



        mDatabaseRef!!.orderByChild("sellerEmail").equalTo("$userEmail").addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@myProduct,error.message, Toast.LENGTH_SHORT).show()
                myDataLoaderProgressBar.visibility = View.INVISIBLE

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                mProducts.clear()
                for (ProductSnapshot in snapshot.children){
                    val upload = ProductSnapshot.getValue(Product ::class.java)
                    upload!!.key = ProductSnapshot.key
                    mProducts.add(upload)
                    Toast.makeText(this@myProduct,userEmail, Toast.LENGTH_SHORT).show()

                }
                listAdapter = ListAdapterMy(this@myProduct, mProducts)
        mRecyclerView.adapter = listAdapter
                myDataLoaderProgressBar.visibility = View.GONE

            }

        })



}}