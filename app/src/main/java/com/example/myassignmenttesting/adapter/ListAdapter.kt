package com.example.myassignmenttesting.adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myassignmenttesting.R
import com.example.myassignmenttesting.DetailsActivity
import com.example.myassignmenttesting.model.Product
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ListAdapter(var mContext:Context, var productList: MutableList<Product>):
RecyclerView.Adapter<ListAdapter.ListViewHolder>()
{
    inner class ListViewHolder(var v:View): RecyclerView.ViewHolder(v){
        var imgT = v.findViewById<ImageView>(R.id.ProductImageView)
        var nameT = v.findViewById<TextView>(R.id.nameTextView)
        var descriT = v.findViewById<TextView>(R.id.descriptionTextView)
        var priceT = v.findViewById<TextView>(R.id.priceTextView)
        var categoryT = v.findViewById<TextView>(R.id.catTextView)
        var quantityT = v.findViewById<TextView>(R.id.quanTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
       var infalter = LayoutInflater.from(parent.context)
        var v = infalter.inflate(R.layout.row_item,parent,false)
        return ListViewHolder(v)
    }

    override fun getItemCount(): Int =productList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
       var newList = productList[position]
        var imageName = newList.imageUrl+".jpg"

        holder.nameT.text = newList.imageUrl
        holder.descriT.text = newList.description

        holder.priceT.text = newList.price.toString()
        holder.categoryT.text = newList.category
        holder.quantityT.text = newList.quantity.toString()

        val storageRef = FirebaseStorage.getInstance().reference.child("Product_images/$imageName")
        val localfile = File.createTempFile("tempImage","jpeg")
        storageRef.getFile(localfile)
            .addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                holder.imgT.setImageBitmap(bitmap)


        holder.v.setOnClickListener {

            val name = newList.name
            val descrip = newList.description
            val imgUri = newList.imageUrl
            val price = newList.price
            val category = newList.category
            val quantity = newList.quantity

            val mIntent = Intent(mContext, DetailsActivity::class.java)
            mIntent.putExtra("NAMET",name)
            mIntent.putExtra("DESCRIT",descrip)
            mIntent.putExtra("IMGURI",imgUri)
            mIntent.putExtra("PRICET",price)
            mIntent.putExtra("CATT",category)
            mIntent.putExtra("QUANT",quantity)

            mContext.startActivity(mIntent)
        }
    }
}
}