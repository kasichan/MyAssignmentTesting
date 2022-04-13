package com.example.myassignmenttesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myassignmenttesting.databinding.BuyerUserLoginBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
//import com.example.myassignmenttesting.databinding.NavHeaderBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home_.*
import kotlinx.android.synthetic.main.fragment_home_.view.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*



class navigation_drawer_activity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var firebaseAuth : FirebaseAuth
    //private lateinit var  header : NavHeaderBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)


        firebaseAuth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()

        verifyUser()
//        replaceFragment(ChatRoomFragment(), "ChatRoom")

        drawerLayout = findViewById(R.id.drawerLayout)


        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout,R.string.nav_open,R.string.nav_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        actionBar = supportActionBar!!
        actionBar.title = "Home"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)



//        replaceFragment(Home_Fragment(), "Home")

var intent = Intent(this,navigation_drawer_activity::class.java)
        var chat = Intent(this,buy_chat_layout_recycle_activity::class.java)

        navView.setNavigationItemSelectedListener {

            it.isChecked = true

            when(it.itemId){
                R.id.nav_home -> startActivity(intent)
                R.id.nav_profile -> replaceFragment(ProfileFragment(), it.title.toString())
                R.id.nav_notification -> replaceFragment(NotificationFragment(), it.title.toString())
                R.id.nav_settings -> replaceFragment(SettingsFragment(), it.title.toString())
                R.id.nav_chat -> startActivity(chat)
                R.id.nav_logout -> logout()
            }
            true
        }
    }

    private fun logout(){

        firebaseAuth.signOut()
        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser==null) {
            startActivity(Intent(this, BuyerLoginActivity::class.java))
            finish()
        }

    }

    private fun replaceFragment(fragment: Fragment, title : String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment,fragment)
        fragmentTransaction.commit()
        setTitle(title)
        drawerLayout.closeDrawers()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    private fun verifyUser(){
        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser!=null) {
            val email1 = firebaseUser.email.toString()


            db.collection("User").whereEqualTo("email", email1).get().addOnSuccessListener {
                for (document in it) {
                  user_name.text = document.get("username").toString()
                   email.text = document.get("email").toString()
                }
            }


        }
        else
        {
            startActivity(Intent(this, BuyerLoginActivity::class.java))
            finish()
        }
    }
}