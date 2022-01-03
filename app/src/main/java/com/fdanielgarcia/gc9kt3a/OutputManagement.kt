package com.fdanielgarcia.gc9kt3a

import android.app.Activity
import android.widget.TextView
import kotlin.math.roundToInt

class OutputManagement(val activity: Activity, val outputTextView: TextView) {
    val initialPosition by lazy { (activity.application as GCApplication).initialPosition }
    val finalPosition by lazy { (activity.application as GCApplication).finalPosition }
    val currentPosition by lazy { (activity.application as GCApplication).currentPosition }

    fun show() {
        if (currentPosition.latitude == 0.0 && currentPosition.longitude == 0.0) {
            outputTextView.text = activity.resources?.getString(R.string.unknown_location)
        } else {
            outputTextView.setTextAppearance(R.style.TextAppearance_AppCompat_Display1)
            outputTextView.text =
                finalPosition.distance(currentPosition).roundToInt().toString() + " m"
        }
    }
}