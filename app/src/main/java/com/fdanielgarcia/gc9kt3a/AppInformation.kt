package com.fdanielgarcia.gc9kt3a

import android.app.Activity

class AppInformation(val activity: Activity) {
    fun showVersion(): String {
        val message = "Version " +
                BuildConfig.VERSION_NAME + " (" +
                BuildConfig.VERSION_CODE + ") " +
                "\n" +
                "\n" +
                "\n" +
                activity.getString(R.string.material_design_resources_license) + "\n" +
                "\n" +
                activity.getString(R.string.geologo_license) + "\n" +
                "\n" +
                activity.getString(R.string.privacy_policy)
        return message
    }
}