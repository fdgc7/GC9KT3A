package com.fdanielgarcia.gc9kt3a

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mislugares.R
import com.example.mislugares.databinding.ActivityMainBinding
import com.example.mislugares.use_cases.CasosUsoActividades
import com.example.mislugares.use_cases.CasosUsoLocalizacion
import com.example.mislugares.use_cases.CasosUsoLugar
import com.google.android.material.snackbar.Snackbar
import java.lang.Integer.parseInt

class TmpActivity : AppCompatActivity() {
/*
    private lateinit var binding: ActivityMainBinding
    val lugares by lazy { (application as Aplicacion).lugares }
    val adaptador by lazy { (application as Aplicacion).adaptador }
    val usoLugar by lazy { CasosUsoLugar(this, lugares) }
    val usoActividades by lazy { CasosUsoActividades(this) }
    val usoLocalizacion by lazy {CasosUsoLocalizacion(this, SOLICITUD_PERMISO_LOCALIZACION) }

    companion object {
        const val SOLICITUD_PERMISO_CALL_PHONE = 0
        const val SOLICITUD_PERMISO_LOCALIZACION = 1

        fun solicitarPermiso(permiso: String, justificacion: String, requestCode: Int, actividad: Activity) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(actividad,permiso)) {
                AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, whichButton ->
                        ActivityCompat.requestPermissions(actividad, arrayOf(permiso), requestCode)
                    }).show()
            } else {
                ActivityCompat.requestPermissions(actividad,arrayOf(permiso), requestCode)
            }
        }
    }
*/
    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view -> mostrarPreferencias(view)
            //Snackbar.make(view, "Changed by fdgc", Snackbar.LENGTH_LONG)
            //    .setAction("Action", null).show()
        }

        lugares.añadeEjemplos()

        adaptador.onClick  =  {
            val pos = binding.include.recyclerView.getChildAdapterPosition(it)
            usoLugar.mostrar(pos)
        }

        binding.include.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@TmpActivity)
            adapter = adaptador
        }
    }
*/
//    override fun onResume() {
//        super.onResume()
//        usoLocalizacion.activar()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        usoLocalizacion.desactivar()
//    }
/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.acercaDe -> {
                usoActividades.lanzarAcerdaDe()
                true
            }
            R.id.action_settings -> {
                lanzarPreferencias()
                true
            }
            R.id.menu_buscar -> {
                lanzarVistaLugar()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
*/
//    override fun onRequestPermissionsResult(requestCode: Int,
//                                            permissions: Array<String>, grantResults: IntArray ) {
//        if (requestCode == SOLICITUD_PERMISO_LOCALIZACION
//            && grantResults.size == 1
//            && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            usoLocalizacion.permisoConcedido()
//    }

    /*private fun lanzarPreferencias(view: View? = null) {
        val i = Intent(this, PreferenciasActivity::class.java)
        startActivity(i)
    }

    private fun mostrarPreferencias(view: View? = null) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val s = "Path: " + Environment.getDataDirectory().toString() +
                ", Notificaciones: " + pref.getBoolean("notificaciones",true) +
                ", Maximo: " + pref.getString("maximo", "?") + ", Orden: " +
                pref.getString("orden", "?")
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }*/

    /*fun lanzarVistaLugar(view: View? = null) {
        val entrada = EditText(this)
        entrada.setText("0")
        AlertDialog.Builder(this)
            .setTitle("Selección de lugar")
            .setMessage("indica su id:")
            .setView(entrada)
            .setPositiveButton("Ok")  { dialog, whichButton ->
                val id = parseInt(entrada.text.toString())
                usoLugar.mostrar(id);
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }*/
}