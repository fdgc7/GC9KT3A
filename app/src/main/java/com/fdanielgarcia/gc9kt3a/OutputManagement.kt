package com.fdanielgarcia.gc9kt3a

import android.app.Activity
import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

class OutputManagement(val activity: Activity, val outputTextView: TextView) {
    val initialPosition = GeoPoint(GCApplication.INITIAL_LATITUDE, GCApplication.INITIAL_LONGITUDE)
    val finalPosition = GeoPoint(Utilities().uObfC(GCApplication.FINAL_LATITUDE), Utilities().uObfC(GCApplication.FINAL_LONGITUDE))
    val currentPosition by lazy { (activity.application as GCApplication).currentPosition }

    fun show() {
        outputTextView.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.primary_text))

        if (currentPosition.latitude == 0.0 && currentPosition.longitude == 0.0) {
            outputTextView.text = activity.resources?.getString(R.string.unknown_location)
        } else {
            val distanceToInitial = initialPosition.distance(currentPosition)
            if (distanceToInitial <= GCApplication.MIN_INITIAL_DISTANCE) {
                val distanceToFinal = finalPosition.distance(currentPosition)
                if (distanceToFinal <= GCApplication.MIN_FINAL_DISTANCE) {
                    outputTextView.text = activity.resources?.getString(R.string.near_final)
                } else {
                    if (distanceToFinal <= GCApplication.MIN_COLOR_DISTANCE) {
                        outputTextView.setTextColor(
                            Color.parseColor(
                                Utilities().rgbScaleBlue2Red(
                                    distanceToFinal.toInt(),
                                    GCApplication.MIN_COLOR_DISTANCE.toInt()
                                )
                            )
                        )
                    }
                    outputTextView.text =
                        distanceToFinal.roundToInt().toString() + " m"
                }
            } else {
                outputTextView.text = activity.resources?.getString(R.string.not_enough_initial_distance)
            }
        }
    }
}