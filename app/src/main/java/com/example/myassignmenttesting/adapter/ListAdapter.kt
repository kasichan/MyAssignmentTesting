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
import com.example.myassignmenttesting.model.Teacher
import com.example.myassignmenttesting.uitel.loadImage
import com.example.myassignmenttesting.DetailsActivity
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ListAdapter (var mContext:Context,var teacherList:List<Teacher>):
RecyclerView.Adapter<ListAdapter.ListViewHolder>()
{
    inner class ListViewHolder(var v:View): RecyclerView.ViewHolder(v){
        var imgT = v.findViewById<ImageView>(R.id.teacherImageView)
        var nameT = v.findViewById<TextView>(R.id.nameTextView)
        var descriT = v.findViewById<TextView>(R.id.descriptionTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
       var infalter = LayoutInflater.from(parent.context)
        var v = infalter.inflate(R.layout.row_item,parent,false)
        return ListViewHolder(v)
    }

    override fun getItemCount(): Int =teacherList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
       var newList = teacherList[position]
        var imageName = newList.imageUrl+".jpg"
        holder.nameT.text = newList.imageUrl
        holder.descriT.text = newList.description

        val storageRef = FirebaseStorage.getInstance().reference.child("teachers_uploads/$imageName")
        val localfile = File.createTempFile("tempImage","jpeg")
        storageRef.getFile(localfile)
            .addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                holder.imgT.setImageBitmap(bitmap)


        holder.v.setOnClickListener {

            val name = newList.name
            val descrip = newList.description
            val imgUri = newList.imageUrl

            val mIntent = Intent(mContext, DetailsActivity::class.java)
            mIntent.putExtra("NAMET",name)
            mIntent.putExtra("DESCRIT",descrip)
            mIntent.putExtra("IMGURI",imgUri)

            mContext.startActivity(mIntent)
        }
    }
}
}