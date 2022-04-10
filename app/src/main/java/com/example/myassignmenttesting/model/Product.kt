package com.example.myassignmenttesting.model


import com.google.firebase.database.Exclude

data class Product(
    var name:String? = null,
    var imageUrl:String? = null,
    var description:String? = null,
    var price:Double?=0.0,
    var category: String?=null,
    var quantity: Int?= null,


    @get:Exclude
    @set:Exclude
    var key:String? = null

)