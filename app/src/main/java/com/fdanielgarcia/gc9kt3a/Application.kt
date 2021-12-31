package com.fdanielgarcia.gc9kt3a

import android.app.Application

class Application : Application() {
    val REFRESH_MIN_TIME = (1 * 1000).toLong() // 1 second
    val REFRESH_MIN_DISTANCE = 1.0F // 1 metre
    val INITIAL_COORDINATES =
        /*
        N 40° 33.200' W 3° 37.067' 40.553333 3.617783
        N 40° 33.136' W 3° 37.100' 40.552267 3.618333
         */
    val currentPosition = GeoPoint(0.0, 0.0)
}