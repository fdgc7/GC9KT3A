package com.fdanielgarcia.gc9kt3a

import android.content.Context
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.fdanielgarcia.gc9kt3a.R

class MainActivity : AppCompatActivity(), LocationListener {
    val TIEMPO_MIN = (10 * 1000).toLong() // 10 segundos
    val DISTANCIA_MIN = 5.0F // 5 metros
    val A = arrayOf("n/d", "preciso", "impreciso")
    val P = arrayOf("n/d", "bajo", "medio", "alto")
    val E = arrayOf("fuera de servicio", "temporalmente no disponible","disponible")
    lateinit var manejador: LocationManager
    lateinit var proveedor: String
    lateinit var salida: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        salida = findViewById(R.id.salida)
        manejador = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        muestraProveedores()
        val criterio = Criteria().apply {
            isCostAllowed = false
            isAltitudeRequired = true
            accuracy = Criteria.ACCURACY_FINE
        }

        val nullableProveedor = manejador.getBestProvider(criterio, true)
        proveedor = nullableProveedor ?: ""

        log("Mejor proveedor: $proveedor\n")
        log("Comenzamos con la última localización conocida:")
        muestraLocaliz(manejador.getLastKnownLocation(proveedor))
    }

    override fun onResume() {
        super.onResume()
        manejador.requestLocationUpdates(proveedor, TIEMPO_MIN, DISTANCIA_MIN,this)
    }

    override fun onPause() {
        super.onPause()
        manejador.removeUpdates(this)
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
    private fun log(cadena: String) = salida.append(cadena + "\n")

    private fun muestraLocaliz(localizacion: Location?) {
        if (localizacion == null)
            log("Localización desconocida\n")
        else
            log(localizacion!!.toString() + "\n")
    }

    private fun muestraProveedores() {
        log("Proveedores de localización: \n ")
        val proveedores = manejador.getAllProviders()
        for (proveedor in proveedores) {
            muestraProveedor(proveedor)
        }
    }

    private fun muestraProveedor(proveedor: String) {
        val location_provider: LocationProvider? = manejador.getProvider(proveedor)

        if (location_provider != null) {
            with(location_provider) {
                log(
                    "LocationProvider[ " + "getName= $name, isProviderEnabled" +
                            "=${manejador.isProviderEnabled(proveedor)}, " +
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
