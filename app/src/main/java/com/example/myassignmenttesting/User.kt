package com.example.myassignmenttesting

data class User(val uid:String, val email:String, val username:String, val password:String, val address:String, val gender:String, val age:Int, val status:String)
data class UserChat(val uid:String = "", val email:String = "", val username:String = "")

