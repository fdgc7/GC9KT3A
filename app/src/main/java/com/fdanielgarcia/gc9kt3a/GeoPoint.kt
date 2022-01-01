package com.fdanielgarcia.gc9kt3a

data class GeoPoint(var latitude: Double, var longitude: Double) {
    companion object {
        val NO_POSITION = GeoPoint(0.0,0.0)
    }

    fun distance(point: GeoPoint): Double {
        val EARTH_RADIO = 6371000.0 // en metros
        val dLat = Math.toRadians(latitude - point.latitude)
        val dLon = Math.toRadians(longitude - point.longitude)
        val lat1 = Math.toRadians(point.latitude)
        val lat2 = Math.toRadians(latitude)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) *
                Math.cos(lat1) * Math.cos(lat2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return c * EARTH_RADIO
    }
}