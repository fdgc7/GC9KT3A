package com.fdanielgarcia.gc9kt3a

import android.app.Application

class GCApplication : Application() {
    val REFRESH_MIN_TIME = (1 * 1000).toLong() // 1 second
    val REFRESH_MIN_DISTANCE = 1.0F // 1 metre
    val INITIAL_LATITUDE = 40.553333
    val INITIAL_LONGITUDE = -3.617783
    val FINAL_LATITUDE = 40.552267
    val FINAL_LONGITUDE = -3.618333
    val MIN_INITIAL_DISTANCE = 300.0F // 300 metres
    val MIN_FINAL_DISTANCE = 5.0F // 5 metres

    val initialPosition = GeoPoint(INITIAL_LATITUDE, INITIAL_LONGITUDE)
    val finalPosition = GeoPoint(FINAL_LATITUDE, FINAL_LONGITUDE)
    val currentPosition = GeoPoint.NO_POSITION
}