package com.example.myassignmenttesting

import android.app.Notification
import android.app.Notification.PRIORITY_DEFAULT
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat.Builder
import androidx.core.app.NotificationCompat.PRIORITY_DEFAULT
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.media.app.NotificationCompat
import com.example.myassignmenttesting.databinding.ChangePasswordBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_change_password.view.*

class ChangePasswordFragment : Fragment() {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var db: FirebaseFirestore

    var oldPassword1 = ""
    var newPassword1 = ""
    var confirmPassword1 = ""

    private val CHANNEL_ID = "channel_id_1"
    private val notificationId = 101

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = inflater.inflate(R.layout.fragment_change_password,container,false)


        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        createNotificationChannel()

       binding.confirmChange.setOnClickListener {
            val firebaseUser = firebaseAuth.currentUser

            if(firebaseUser!=null){
                val email = firebaseUser.email.toString()


                oldPassword1 = oldPassword.text.toString()
                newPassword1 = newPassword.text.toString()
                confirmPassword1 = changeConfirmPassword.text.toString()

                db.collection("User").whereEqualTo("email", email).get().addOnSuccessListener {
                    for (document in it)
                    {
                        if(oldPassword1.equals(document.get("password").toString())){
                            if(newPassword1.length<6){
                               newPassword.error = "Password must at least 6 characters"
                            }
                            else if(confirmPassword1 != newPassword1){
                           changeConfirmPassword.error = "Confirm password not match"
                            }
                            else{
                                val username = document.get("username").toString()
                                val address = document.get("address").toString()
                                val gender = document.get("gender").toString()
                                val age = Integer.parseInt(document.get("age").toString())
                                val status = document.get("status").toString()
                                val user = User("",email, username,newPassword1,address,gender,age,status)
                                db.collection("User").document(email).set(user).addOnSuccessListener {

                                    firebaseUser!!.updatePassword(newPassword1)
                                        .addOnSuccessListener { task ->
//                                            if (task.isSuccessful) {
                                                Toast.makeText(activity, "Your password has been updated"  , Toast.LENGTH_SHORT).show()
//                                            }
                                        }

                                    sendNotification()
                                    Toast.makeText(activity, "Your password has been updated"  , Toast.LENGTH_SHORT).show()

                                }
                            }
                        }
                        else{
                            Toast.makeText(activity, "Old password not match ${document.get("password")}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } //if no user logged In
            else{
                val intent = Intent(activity, BuyerLoginActivity::class.java)
                startActivity(intent)

            }
        }

        // Inflate the layout for this fragment
        return binding
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Security Reminder"
            val descriptionText = "Password has been updated"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(){

//        val intent = Intent(activity,ChangePasswordFragment::class.java).apply{
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(activity,0,intent,0)
//
//        val bitmap = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.)

        var builder = Notification.Builder(activity, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_shopping_bag_24)
            .setContentTitle("Security Reminder")
            .setContentText("Password has been updated")



        with(NotificationManagerCompat.from(requireContext())){
            notify(notificationId,builder.build())
        }
    }


}