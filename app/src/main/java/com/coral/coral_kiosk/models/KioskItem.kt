package com.coral.coral_kiosk.models

import android.os.Parcelable
import androidx.annotation.FloatRange
import kotlinx.parcelize.Parcelize


@Parcelize
/**
 * Data class to represent a salable item. Please note that SKU's uniqueness is _not_ being enforced
 * locally in the app. This would presumably be in the backend database. If we needed a local
 * solution, we'd probably set up a ROOM database.
 *
 * @param name the product's official name
 * @param description a deeper description of the product
 * @param SKU the Stock Keeping Unit, a unique identifier for the product
 * @param lat the latitude of the supplier
 * @param long the longitude of the supplier
 */
data class KioskItem(
    val name: String,
    val description: String,
    val SKU: String,
    @FloatRange(0.0) val price: Double,
    val lat: Double,
    val long: Double
) : Parcelable
