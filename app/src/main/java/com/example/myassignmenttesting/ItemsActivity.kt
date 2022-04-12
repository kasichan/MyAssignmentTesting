package com.example.myassignmenttesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.example.myassignmenttesting.adapter.ListAdapter
import com.example.myassignmenttesting.model.Product
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.row_item.*


class ItemsActivity : AppCompatActivity() {

    private var mStorage:FirebaseStorage? = null
    private var mDatabaseRef:DatabaseReference? = null
    private var mDBListener:ValueEventListener? = null
    private lateinit var mProducts:MutableList<Product>
    private lateinit var listAdapter:ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        /**set adapter*/
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this@ItemsActivity)
        myDataLoaderProgressBar.visibility = View.VISIBLE
        mProducts = ArrayList()
        listAdapter = ListAdapter(this@ItemsActivity,mProducts)
        mRecyclerView.adapter = listAdapter

        /**set Firebase Database*/
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Product_images")

        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ItemsActivity,error.message, Toast.LENGTH_SHORT).show()
                myDataLoaderProgressBar.visibility = View.INVISIBLE

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                mProducts.clear()
                for (ProductSnapshot in snapshot.children){
                    val upload = ProductSnapshot.getValue(Product ::class.java)
                    upload!!.key = ProductSnapshot.key
                    mProducts.add(upload)

                }
                listAdapter.notifyDataSetChanged()
                myDataLoaderProgressBar.visibility = View.GONE

            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        mDatabaseRef!!.removeEventListener(mDBListener!!)
    }

}