package com.example.myassignmenttesting

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.myassignmenttesting.databinding.ActivityMapsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class Maps_Activity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private var locationArrayList: ArrayList<LatLng>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intss = intent
        var senderEmailLoc = intss.getStringExtra("senderEmail")
        var senderLocation = intss.getStringExtra("senderAddress")


        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationArrayList = ArrayList()


        var ltt: Double = 0.00000000
        var lgt: Double = 0.00000000
        var testint = LatLng(ltt, lgt)
        val zooming = 15f

        val geocode = Geocoder(this, Locale.getDefault())

        val addList = geocode.getFromLocationName(senderLocation, 1)
        ltt = addList[0].latitude.toDouble()
        lgt = addList[0].longitude.toDouble()


        val userEmail = senderEmailLoc

        db.collection("User").whereEqualTo("email", senderEmailLoc).get().addOnSuccessListener {
            for (document in it) {
                Toast.makeText(this, senderEmailLoc, Toast.LENGTH_SHORT).show()
                var secandLongLa1 =
                    LatLng(document.get("lang") as Double, document.get("long") as Double)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(secandLongLa1, zooming))
                map.addMarker(MarkerOptions().position(secandLongLa1))

            }


        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Change the map type based on the user's selection.
        R.id.normal_map_option -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map_option -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map_option -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map_option -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


}