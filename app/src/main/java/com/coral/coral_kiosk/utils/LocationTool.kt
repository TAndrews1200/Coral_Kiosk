package com.coral.coral_kiosk.utils

import android.Manifest.permission
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.annotation.RequiresPermission
import com.coral.coral_kiosk.models.KioskItem

object LocationTool {

    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    fun getLocation(locationManager: LocationManager): Location? {
        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        var returnableLocation: Location? = null
        var locationByNetwork: Location? = null
        var locationByGps: Location? = null

        val gpsLocationListener =
            LocationListener { location -> locationByGps = location }
        val networkLocationListener =
            LocationListener { location -> locationByNetwork = location }

        if (hasGps) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0F,
                gpsLocationListener
            )
        }
        if (hasNetwork) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                0F,
                networkLocationListener
            )
        }

        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        lastKnownLocationByGps?.let {
            locationByGps = lastKnownLocationByGps
        }
        val lastKnownLocationByNetwork =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        lastKnownLocationByNetwork?.let {
            locationByNetwork = lastKnownLocationByNetwork
        }

        if (locationByGps != null && locationByNetwork != null) {
            /*
             Please note, "accuracy" returns a value equal to the radius in which it believes
             the user likely is relative to the lat/long given. There's no guarantee they will
             be in this radius, but the important takeaway is that the _smaller_ value is better.

             The "if" should prevent the elvis operators from firing off, but it's an extra
             layer of safety.
             */
            if ((locationByGps?.accuracy ?: Float.MAX_VALUE) <
                (locationByNetwork?.accuracy ?: Float.MAX_VALUE)
            ) {
                returnableLocation = locationByGps
            } else {
                returnableLocation = locationByNetwork
            }
        }
        return returnableLocation
    }

    /**
     * Calculates the distance from the given location to the item's coordinates
     * @param location the given location, presumably of the user
     * @param item the KioskItem we want to compare to.
     * @return distance between in meters, as a Float
     */
    fun distanceToItem(location: Location, item: KioskItem): Float {
        val results = FloatArray(1)
        Location.distanceBetween(location.latitude, location.longitude, item.lat, item.long, results)
        return results[0]
    }
}