package com.coral.coral_kiosk.models

import android.os.Parcelable
import androidx.annotation.FloatRange
import kotlinx.parcelize.Parcelize


@Parcelize
/**
 * Data class to represent a salable item. Please note that cartFragmentToCheckoutButton's uniqueness is _not_ being enforced
 * locally in the app. This would presumably be in the backend database. If we needed a local
 * solution, we'd probably set up a ROOM database.
 *
 * @param name the product's official name
 * @param description a deeper description of the product
 * @param price the price of the good
 * @param lat the latitude of the supplier
 * @param long the longitude of the supplier
 */
data class KioskItem(
    val name: String,
    val description: String,
    @FloatRange(0.0) val price: Double,
    val lat: Double,
    val long: Double
) : Parcelable
