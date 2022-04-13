
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
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myassignmenttesting.databinding.FragmentSearchBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_search.view.*

import kotlinx.android.synthetic.main.navigation_drawer.view.*
import androidx.appcompat.app.AppCompatActivity





class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var firebaseAuth : FirebaseAuth

    private lateinit var actionBar: ActionBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = inflater.inflate(R.layout.fragment_search, container, false)

        actionBar = (activity as AppCompatActivity?)!!.supportActionBar!!
        actionBar.title = "Search"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        firebaseAuth = FirebaseAuth.getInstance()

        val v: View = inflater.inflate(R.layout.navigation_drawer, container, false)
        drawerLayout = v.drawerLayout
        val navView: NavigationView = v.nav_view


        toggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val intent = Intent(activity, buy_chat_layout_recycle_activity::class.java)


        navView.setNavigationItemSelectedListener {

            it.isChecked = true

            when(it.itemId){
                R.id.nav_home -> replaceFragment(Home_Fragment(), it.title.toString())
                R.id.nav_profile -> replaceFragment(ProfileFragment(), it.title.toString())
//                R.id.nav_notification -> replaceFragment(NotificationFragment(), it.title.toString())
                R.id.nav_settings -> replaceFragment(SettingsFragment(), it.title.toString())
                R.id.nav_chat ->    startActivity(intent)
                R.id.nav_logout -> logout()
            }
            true
        }

        binding.chat.setOnClickListener {
            startActivity(intent)
        }

        return binding
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager = getParentFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment, fragment)
        fragmentTransaction.commit()
        //setTitle(title)
        drawerLayout.closeDrawers()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun logout() {

        firebaseAuth.signOut()
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser == null) {
            val intent = Intent(activity, BuyerLoginActivity::class.java)
            startActivity(intent)
        }

    }


}