package com.coral.coral_kiosk

import android.location.Location

data class KioskItem(
    val name: String,
    val description: String,
    val lat: Double,
    val long: Double,

) {
    /** Calculates the distance from the given coordinates to the item's coordinates
     * @param userLat the user's latitude
     * @param userLong the user's longitude
     * @return distance between as a Float.
     *
     */
    fun distanceFrom(userLat: Double, userLong: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(userLat, userLong, lat, long, results)
        return results[0]
    }
}
