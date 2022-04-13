package com.example.myassignmenttesting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myassignmenttesting.databinding.FragmentProfileBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.navigation_drawer.view.*

class ProfileFragment : Fragment() {

    //private lateinit var binding: FragmentProfileBinding

    private lateinit var firebaseAuth : FirebaseAuth

    private lateinit var db: FirebaseFirestore

    private lateinit var actionBar: ActionBar



    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = inflater.inflate(R.layout.fragment_profile,container,false)

        actionBar = (activity as AppCompatActivity?)!!.supportActionBar!!
        actionBar.title = "Profile"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        firebaseAuth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()

        verifyUser()

        val v: View = inflater.inflate(R.layout.navigation_drawer, container, false)
        drawerLayout = v.drawerLayout
        val navView : NavigationView = v.nav_view


        toggle = ActionBarDrawerToggle(activity, drawerLayout,R.string.nav_open,R.string.nav_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.setting.setOnClickListener {
            replaceFragment(SettingsFragment(), "Settings")

        }



        binding.seller_center.setOnClickListener {
            val seller = Intent(activity, seller_dashboard_activity::class.java)
            startActivity(seller)


        }

        binding.chat_button.setOnClickListener {
            val intent = Intent(activity, buy_chat_layout_recycle_activity::class.java)
            startActivity(intent)
        }


        binding.myPur.setOnClickListener {
            val intent = Intent(activity, myPurchase::class.java)
            startActivity(intent)
        }



        return binding
    }

    private fun verifyUser(){
        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser!=null) {
            val email = firebaseUser.email.toString()


            db.collection("User").whereEqualTo("email", email).get().addOnSuccessListener {
                for (document in it) {
                    username.setText(document.get("username").toString())
                }
            }


        }
        else
        {
            val intent = Intent(activity, BuyerLoginActivity::class.java)
            startActivity(intent)

        }
    }

    private fun replaceFragment(fragment: Fragment, title : String) {
        val fragmentManager = getParentFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment,fragment)
        fragmentTransaction.commit()
        //setTitle(title)
        drawerLayout.closeDrawers()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}