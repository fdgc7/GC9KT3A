package com.fdanielgarcia.gc9kt3a

import android.app.Application

class GCApplication : Application() {
    companion object {
        const val UPDATE_MIN_TIME = (1 * 1000).toLong() // 1 second
        const val UPDATE_MIN_DISTANCE = 1.0F // 1 metre
        const val INITIAL_LATITUDE = 40.553333
        const val INITIAL_LONGITUDE = -3.617783
        const val FINAL_LATITUDE = "5D7A6D141AFF9"
        const val FINAL_LONGITUDE = "-BE84BE63F49"
        const val MIN_INITIAL_DISTANCE = 300.0F // 300 metres
        const val MIN_COLOR_DISTANCE = 150.0F // 300 metres
        const val MIN_FINAL_DISTANCE = 5.0F // 5 metres
        const val MAX_NUM_ABOUT = 7
    }

    val currentPosition = GeoPoint.NO_POSITION
}