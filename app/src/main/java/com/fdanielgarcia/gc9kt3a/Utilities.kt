package com.fdanielgarcia.gc9kt3a

import android.content.Context
import android.location.Location
import android.provider.Settings
import android.util.Log

class Utilities {
    fun uObfC(string: String) = (if (string.first().toString() == "-") kotlin.math.sqrt(string.substring(1).toLong(16).toDouble()) / "F4240".toLong(16).toInt() * -1 else kotlin.math.sqrt(string.toLong(16).toDouble()) / "F4240".toLong(16).toInt())

    fun rgbScaleBlue2Red(scaleStep: Int, scaleTotal: Int): String {
        val intBlue = ((255 * scaleStep) / GCApplication.MIN_COLOR_DISTANCE).toInt()
        val intRed = 255 - intBlue
        val hexBlue = String.format("%1$02X",intBlue)
        val hexRed = String.format("%1$02X",intRed)

        val hexColor = "#" + hexRed + "00" + hexBlue

        Log.d("GC9KT3A_tag","Distance: " + scaleStep.toString())
        Log.d("GC9KT3A_tag","Color: " + hexColor)

        return hexColor
    }

    fun isLocationFromMockProvider(context: Context, location: Location): Boolean {
        var isMock = false
        if (android.os.Build.VERSION.SDK_INT >= 18) {
            isMock = location.isFromMockProvider
        } else {
            isMock = !Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")
        }
        return isMock
    }
}