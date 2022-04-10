package com.example.myassignmenttesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_confirmation_fragment.view.*

class confirmation_fragment : DialogFragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView: View =
            inflater.inflate(R.layout.activity_confirmation_fragment, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        rootView.confirmButton.setOnClickListener {
            val firebaseUser = firebaseAuth.currentUser

            if (firebaseUser != null) {
                val email = firebaseUser.email.toString()


                db.collection("User").whereEqualTo("email", email).get().addOnSuccessListener {
                    for (document in it) {


                        val status = document.get("status").toString()
                        val user = User(
                            email,
                            document.get("username").toString(),
                            document.get("password").toString(),
                            document.get("address").toString(),
                            document.get("gender").toString(),
                            Integer.parseInt(document.get("age").toString()),
                            "deactivated"
                        )
                        db.collection("User").document(email).set(user).addOnSuccessListener {
                            Toast.makeText(getActivity(), "Thanks for having us, ByeBye", Toast.LENGTH_SHORT).show()
                            firebaseAuth.signOut()
                            firebaseUser.delete()
                            val intent = Intent(activity, BuyerLoginActivity::class.java)
                            startActivity(intent)

                        }
                    }
                }

            } else {
                val intent = Intent(activity, BuyerLoginActivity::class.java)
                startActivity(intent)
            }
        }

        rootView.cancelButton.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}