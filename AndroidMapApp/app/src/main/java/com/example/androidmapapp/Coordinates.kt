package com.example.androidmapapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import  android.widget.EditText
import android.widget.Button
import android.content.Context;
import android.content.pm.PackageManager
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import androidx.core.app.ActivityCompat

// Handles getting a coordinate text field input and sending it to the map.
class Coordinates : AppCompatActivity() {

    // Create a button and the page view.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinates2)

        // Submit button to send lat/long to the map activity.
        val bSubmit: Button = findViewById(R.id.b_Submit)
        bSubmit.setOnClickListener { getCoordinates() }
    }

    // Retrieve the coordinates in the text field to send them to the map activity.
    private fun getCoordinates(){
        // Get lat/long coordinates from edittext.
        var latitude: EditText = findViewById(R.id.t_lat)
        var longitude: EditText = findViewById(R.id.t_long)

        // Check for valid coordinate in text box.
        if(validateCoordinate(latitude, longitude)) {
            // Send lat/long to the map activity.
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Latitude", latitude.text.toString())
            intent.putExtra("Longitude", longitude.text.toString())
            startActivity(intent)
        }
    }

    // Validate lat/long for a valid coordinate.
    private fun validateCoordinate(latitude: EditText, longitude:EditText): Boolean{
        // Valid if filled and in range of -90/90 / -180/180.
        return if (latitude.text.toString().isEmpty() || latitude.text.toString().toDouble() > 90
            || latitude.text.toString().toDouble() < -90){
            latitude.error = "Latitude value is required and must be between -90 and 90."
            false
        } else if (longitude.text.toString().isEmpty() || longitude.text.toString().toDouble() > 180
            || longitude.text.toString().toDouble() < -180){
            longitude.error = "Longitude value is required and must be between -180 and 180."
            false
        } else{
            true
        }
    }

    // Request permission to access gps and get the current user location from it.
    private fun requestLocation() {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener: LocationListener = LocationListener {  }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)

    }
}
