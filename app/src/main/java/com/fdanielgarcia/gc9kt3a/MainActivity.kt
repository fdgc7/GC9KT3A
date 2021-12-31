package com.fdanielgarcia.gc9kt3a

import android.content.Context
import android.location.*
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity(), LocationListener {
    val A = arrayOf("n/d", "preciso", "impreciso")
    val P = arrayOf("n/d", "bajo", "medio", "alto")
    val E = arrayOf("fuera de servicio", "temporalmente no disponible","disponible")
    lateinit var locationManager: LocationManager
    lateinit var locationProvider: String
    lateinit var output: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        output = findViewById(R.id.salida)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        muestraProveedores()
        val criterio = Criteria().apply {
            isCostAllowed = false
            isAltitudeRequired = true
            accuracy = Criteria.ACCURACY_FINE
        }

        val nullableProveedor = locationManager.getBestProvider(criterio, true)
        locationProvider = nullableProveedor ?: ""

        log("Mejor proveedor: $locationProvider\n")
        log("Comenzamos con la última localización conocida:")
        muestraLocaliz(locationManager.getLastKnownLocation(locationProvider))
    }

    override fun onResume() {
        super.onResume()
        locationManager.requestLocationUpdates(locationProvider, (application as Application).REFRESH_MIN_TIME, (application as Application).REFRESH_MIN_DISTANCE,this)
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(this)
    }

    // Métodos de la interfaz LocationListener
    override fun onLocationChanged(location: Location) {
        log("Nueva localización: ")
        muestraLocaliz(location)
    }

    override fun onProviderDisabled(proveedor: String) {
        log("Proveedor deshabilitado: $proveedor\n")
    }

    override fun onProviderEnabled(proveedor: String) {
        log("Proveedor habilitado: $proveedor\n")
    }

    override fun onStatusChanged(proveedor: String, estado: Int,
                                 extras: Bundle) {
        log("Cambia estado proveedor: $proveedor, estado=" + " ${E[Math.max(0, estado)]}, extras= $extras\n")
    }

    // Métodos para mostrar información
    private fun log(cadena: String) = output.append(cadena + "\n")

    private fun muestraLocaliz(localizacion: Location?) {
        if (localizacion == null)
            log("Localización desconocida\n")
        else
            log(localizacion!!.toString() + "\n")
    }

    private fun muestraProveedores() {
        log("Proveedores de localización: \n ")
        val proveedores = locationManager.getAllProviders()
        for (proveedor in proveedores) {
            muestraProveedor(proveedor)
        }
    }

    private fun muestraProveedor(proveedor: String) {
        val location_provider: LocationProvider? = locationManager.getProvider(proveedor)

        if (location_provider != null) {
            with(location_provider) {
                log(
                    "LocationProvider[ " + "getName= $name, isProviderEnabled" +
                            "=${locationManager.isProviderEnabled(proveedor)}, " +
                            "getAccuracy=${A[Math.max(0, accuracy)]}, " +
                            "getPowerRequirement=${P[Math.max(0, powerRequirement)]}, " +
                            "hasMonetaryCost=${hasMonetaryCost()}, " +
                            "requiresCell=${requiresCell()}, " +
                            "requiresNetwork=${requiresNetwork()}, " +
                            "requiresSatellite=${requiresSatellite()}, " +
                            "supportsAltitude=${supportsAltitude()}, " +
                            "supportsBearing=${supportsBearing()}, " +
                            "supportsSpeed=${supportsSpeed()} ]\n"
                )
            }
        }
    }
}
