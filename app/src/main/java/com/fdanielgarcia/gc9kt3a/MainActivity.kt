package com.fdanielgarcia.gc9kt3a

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.fdanielgarcia.gc9kt3a.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LocationListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var locationManager: LocationManager
    private lateinit var locationProvider: String
    private lateinit var outputTextView: TextView
    private var numAbout = 0

    companion object {
        const val LOCATION_REQUEST_CODE = 0
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(
            this.resources.getString(R.string.app_tag),
            "INITIAL_LATITUDE: " + GCApplication.INITIAL_LATITUDE.toString()
        )
        Log.d(
            this.resources.getString(R.string.app_tag),
            "INITIAL_LONGITUDE: " + GCApplication.INITIAL_LONGITUDE.toString()
        )
        Log.d(
            this.resources.getString(R.string.app_tag),
            "FINAL_LATITUDE: " + Utilities().uObfC(GCApplication.FINAL_LATITUDE).toString()
        )
        Log.d(
            this.resources.getString(R.string.app_tag),
            "FINAL_LONGITUDE: " + Utilities().uObfC(GCApplication.FINAL_LONGITUDE).toString()
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = this.resources.getString(R.string.app_label)
        outputTextView = binding.include.output

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationProvider = LocationManager.GPS_PROVIDER
        lastLocation()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_about -> {
                numAbout ++
                if (numAbout < GCApplication.MAX_NUM_ABOUT) {
                    AlertDialog.Builder(this)
                        .setTitle(
                            this.resources?.getString(R.string.app_label) + " (" + this.resources?.getString(
                                R.string.app_name
                            ) + ")"
                        )
                        .setMessage(AppInformation(this).showVersion())
                        .show()
                } else {
                    val lat = Utilities().uObfC(GCApplication.FINAL_LATITUDE).toString()
                    val lon = Utilities().uObfC(GCApplication.FINAL_LONGITUDE).toString()
                    val pin = this.resources?.getString(R.string.app_name)
                    val uri = Uri.parse("geo:0,0?q=$lat,$lon($pin)")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        enableLocationUpdates()
    }


    // LocationListener management
    override fun onLocationChanged(location: Location) {
        manageLocation(location)
    }

    override fun onProviderEnabled(proveedor: String) {
        enableLocationUpdates()
    }

    override fun onProviderDisabled(proveedor: String) {
        enableLocationUpdates()
    }

    override fun onStatusChanged(
        proveedor: String, estado: Int,
        extras: Bundle
    ) {
        enableLocationUpdates()
    }


    // Permissions management
    private fun requestPermission(permission: String, justification: String, requestCode: Int) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            AlertDialog.Builder(this)
                .setTitle(this.resources?.getString(R.string.permission_request))
                .setMessage(justification)
                .setPositiveButton("Ok") { dialog, whichButton ->
                    ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
                }.show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_REQUEST_CODE
            && grantResults.size == 1
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        )
            locationPermissionGranted()
    }

    private fun checkLocationPermission() =
        (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)

    private fun locationPermissionGranted() {
        lastLocation()
        enableLocationUpdates()
    }


    // Location management
    private fun lastLocation() {
        if (checkLocationPermission()) {
            manageLocation(locationManager.getLastKnownLocation(locationProvider))
        } else {
            requestPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                this.resources.getString(R.string.location_permission_justification),
                LOCATION_REQUEST_CODE
            )
        }
    }

    private fun enableLocationUpdates() {
        if (checkLocationPermission()) {
            locationManager.requestLocationUpdates(
                locationProvider,
                GCApplication.UPDATE_MIN_TIME,
                GCApplication.UPDATE_MIN_DISTANCE,
                this
            )
        } else {
            requestPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                this.resources.getString(R.string.location_permission_justification),
                LOCATION_REQUEST_CODE
            )
        }
    }

    private fun disableLocationUpdates() {
        locationManager.removeUpdates(this)
    }

    private fun manageLocation(location: Location?) {
        if (location == null) {
            (application as GCApplication).currentPosition.latitude = 0.0
            (application as GCApplication).currentPosition.longitude = 0.0

            OutputManagement(this,outputTextView).show()
        } else {
            if (Utilities().isLocationFromMockProvider(applicationContext, location)) {
                AlertDialog.Builder(this)
                    .setTitle(this.resources?.getString(R.string.do_not_cheat))
                    .setMessage(this.resources?.getString(R.string.deactivate_mock_location))
                    .setPositiveButton("Ok") { _, _ -> finish() }.show()
            } else {
                (application as GCApplication).currentPosition.latitude = location.latitude
                (application as GCApplication).currentPosition.longitude = location.longitude

                Log.d(
                    this.resources.getString(R.string.app_tag),
                    "CURRENT_LATITUDE: " + (application as GCApplication).currentPosition.latitude.toString()
                )
                Log.d(
                    this.resources.getString(R.string.app_tag),
                    "CURRENT_LONGITUDE: " + (application as GCApplication).currentPosition.longitude.toString()
                )

                OutputManagement(this, outputTextView).show()
            }
        }
    }
}
