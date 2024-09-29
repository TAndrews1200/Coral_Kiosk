package com.coral.coral_kiosk.models

import android.location.Location
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KioskItem(
    val name: String,
    val description: String,
    val SKU: String,
    val lat: Double,
    val long: Double
) : Parcelable {
    /**
     * Calculates the distance from the given coordinates to the item's coordinates
     * @param userLat the user's latitude
     * @param userLong the user's longitude
     * @return distance between as a Float.
     */
    fun distanceFrom(userLat: Double, userLong: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(userLat, userLong, lat, long, results)
        return results[0]
    }
}
