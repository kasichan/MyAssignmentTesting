package com.example.myassignmenttesting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myassignmenttesting.databinding.FragmentProfileBinding
import com.example.myassignmenttesting.databinding.FragmentSettingsBinding
import com.example.myassignmenttesting.databinding.ProfileSettingsBinding
import com.example.myassignmenttesting.ui.main.BuyerLoginActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home_.view.*
import kotlinx.android.synthetic.main.fragment_settings.view.*



import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*
import kotlinx.android.synthetic.main.navigation_drawer.view.*


class SettingsFragment : Fragment() {
   // private lateinit var binding: FragmentSettingsBinding
    private lateinit var firebaseAuth : FirebaseAuth

    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBar: ActionBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.fragment_settings,container,false)


        actionBar = (activity as AppCompatActivity?)!!.supportActionBar!!
        actionBar.title = "Settings"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        firebaseAuth = FirebaseAuth.getInstance()

        val v: View = inflater.inflate(R.layout.navigation_drawer, container, false)
        drawerLayout = v.drawerLayout
        val navView : NavigationView = v.nav_view


        toggle = ActionBarDrawerToggle(activity, drawerLayout,R.string.nav_open,R.string.nav_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        binding.editProfile.setOnClickListener {
           // Navigation.findNavController(binding).navigate(R.id.action_home_Fragment_to_editProfileFragment)
            replaceFragment(EditProfileFragment(), "edit profile")

        }

        binding.changePassword.setOnClickListener {
            //Navigation.findNavController(binding).navigate(R.id.action_home_Fragment_to_changePasswordFragment)
            replaceFragment(ChangePasswordFragment(), "change password")
        }

        binding.contactUs.setOnClickListener {
            //Navigation.findNavController(binding).navigate(R.id.action_home_Fragment_to_contactUsFragment)
            replaceFragment(ContactUsFragment(), "contact us")

        }

        navView.setNavigationItemSelectedListener {

            it.isChecked = true

            when(it.itemId){
                R.id.nav_home -> replaceFragment(Home_Fragment(), it.title.toString())
                R.id.nav_profile -> replaceFragment(ProfileFragment(), it.title.toString())
//                R.id.nav_notification -> replaceFragment(NotificationFragment(), it.title.toString())
                R.id.nav_settings -> replaceFragment(SettingsFragment(), it.title.toString())
                R.id.nav_chat -> replaceFragment(Chat_Fragment(), it.title.toString())
                R.id.nav_logout -> logout()
            }
            true
        }

        binding.deactivateAccount.setOnClickListener {
            deactivate()
        }

        binding.logout.setOnClickListener {
            logout()
        }

        return binding
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





    private fun logout(){

        firebaseAuth.signOut()
        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser==null) {
            val intent = Intent(activity, BuyerLoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun deactivate(){
        val dialog = confirmation_fragment()
        dialog.show(childFragmentManager,"customDialog")

    }


}