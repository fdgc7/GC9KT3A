package com.fdanielgarcia.gc9kt3a

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
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
import com.fdanielgarcia.gc9kt3a.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), LocationListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var locationManager: LocationManager
    private lateinit var locationProvider: String
    private lateinit var output: TextView

    companion object {
        const val LOCATION_REQUEST_CODE = 0
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        output = binding.include.output

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationProvider = LocationManager.GPS_PROVIDER
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
        if (location == null)
            output.text = this.resources?.getString(R.string.unknown_location)
        else {
            (application as GCApplication).currentPosition.latitude = location.latitude
            (application as GCApplication).currentPosition.longitude = location.longitude

            output.setTextAppearance(R.style.TextAppearance_AppCompat_Display1)
            output.text = (application as GCApplication).finalPosition.distance((application as GCApplication).currentPosition).roundToInt().toString() + " m"
        }
    }
}
