package com.example.androidmapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
// ArcGis
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.MapView
import com.example.androidmapapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // From arcgis documentation.
    private val activityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // From arcgis documentation.
    private val mapView: MapView by lazy {
        activityMainBinding.mapView
    }

    // set up your map here. You will call this method from onCreate()
    private fun setupMap() {

        // API key
        ArcGISRuntimeEnvironment.setApiKey("DEMOKEY")

        // create a map with a specified style.
        val map = ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC)

        // set the map to be displayed in the layout's MapView
        mapView.map = map
        // set the viewpoint, Viewpoint(latitude, longitude, scale) defaults to rexburg.
        mapView.setViewpoint(Viewpoint(43.8234, -111.79473, 300000.0))

    }

    // Start an activity to input new map coordinates.
    private fun inputCoordinates() {
        val intent = Intent(this, Coordinates::class.java)
        startActivity(intent)
    }

    // Check for new coordinates to move to when the app returns.
    override fun onResume() {
        super.onResume()

        // Get coordinates and move map to them if not null.
        var latitude = intent.getStringExtra("Latitude")?.toDouble()
        var longitude = intent.getStringExtra("Longitude")?.toDouble()
        if (latitude != null && longitude != null) {
            latitude = latitude.toDouble()
            longitude = longitude.toDouble()
            mapView.setViewpoint(Viewpoint(latitude, longitude, 32000.0))
        }
        //48.8566 2.3522 paris

        mapView.resume()
    }

    override fun onPause() {
        mapView.pause()
        super.onPause()
    }

    // Destroy the map when the app is killed.
    override fun onDestroy() {
        mapView.dispose()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        setupMap()

        // Button to go to coordinate inputs.
        val bInput: Button = findViewById(R.id.input_coordinates)
        bInput.setOnClickListener { inputCoordinates() }
    }
}