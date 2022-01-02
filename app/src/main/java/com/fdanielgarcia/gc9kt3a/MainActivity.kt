package com.fdanielgarcia.gc9kt3a

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity(), LocationListener {
    val LOCATION_REQUEST_CODE = 0
    lateinit var locationManager: LocationManager
    lateinit var locationProvider: String
    lateinit var output: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        output = findViewById(R.id.output)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria().apply {
            isCostAllowed = false
            isAltitudeRequired = true
            accuracy = Criteria.ACCURACY_FINE
        }
        locationProvider = locationManager.getBestProvider(criteria, true) ?: ""

        lastLocation()
    }

    override fun onResume() {
        super.onResume()
        enableLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        disableLocationUpdates()
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
    fun requestPermission(permission: String, justification: String, requestCode: Int) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            AlertDialog.Builder(this)
                .setTitle(this.resources?.getString(R.string.permission_request))
                .setMessage(justification)
                .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, whichButton ->
                    ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
                }).show()
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
            LocationPermissionGranted()
    }

    private fun checkLocationPermission() =
        (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)

    private fun LocationPermissionGranted() {
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
                (application as GCApplication).UPDATE_MIN_TIME,
                (application as GCApplication).UPDATE_MIN_DISTANCE,
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
        if (location == null)
            output.text = this.resources?.getString(R.string.unknown_location)
        else {
            (application as GCApplication).currentPosition.latitude = location.latitude
            (application as GCApplication).currentPosition.longitude = location.longitude

            output.text =
                (application as GCApplication).finalPosition.distance((application as GCApplication).currentPosition)
                    .toString() + " m"
        }
    }

}
