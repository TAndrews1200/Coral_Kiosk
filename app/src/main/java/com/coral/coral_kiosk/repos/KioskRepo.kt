package com.coral.coral_kiosk.repos

import com.coral.coral_kiosk.models.KioskItem
import javax.inject.Inject

/** Normally I expect that this would be fetched from an endpoint, using
 * Retrofit or GraphQL then storing it locally for a short time. As we have no such endpoint, and
 * generally the implementation has a lot of questions that go beyond the engineering department
 * (How long it should be alive, retry strategy, etc) this will be assuming the data is always
 * valid. We are *extremely* confident in our supply chain and ability to meet demand.
 */
interface KioskRepo {
    fun getKioskItems(): List<KioskItem>
}

class KioskRepoImpl @Inject constructor() : KioskRepo {
    override fun getKioskItems(): List<KioskItem> {
        return KioskItemList
    }
}


private val KioskItemList = listOf(
    KioskItem(
        "Item Zero",
        "An item defined by it's existence at 0', 0' on the map. Luckily it's waterproof.",
        0.0,
        0.0
    ),
    KioskItem(
        "Item One",
        "Strictly speaking, it's the second item. That's indices, baby!",
        30.0,
        30.0
    ),
    KioskItem(
        "Item Two",
        "It's rather negative, but try to be understanding",
        -15.0,
        -15.0
    ),
    KioskItem(
        "THE Tony Packo Hot Dog",
        "A favorite of corpsmen everywhere.",
        41.65997804188749,
        -83.50212186996635
    ),
)