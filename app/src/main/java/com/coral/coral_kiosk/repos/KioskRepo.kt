package com.coral.coral_kiosk.repos

import android.util.Log
import com.coral.coral_kiosk.models.KioskItem
import javax.inject.Inject

/**
 * A repository/API used to interact with the KioskItems.
 *
 * Normally I expect that this would be fetched from an endpoint, using
 * Retrofit or GraphQL then storing it locally for a short time. As we have no such endpoint, and
 * generally the implementation has a lot of questions that go beyond the engineering department
 * (How long it should be alive, retry strategy, etc) this will be assuming the data is always
 * valid. We are *extremely* confident in our supply chain and ability to meet demand.
 */
interface KioskRepo {

    /**
     * Gets the list of available items, presumably this would normally be from the server to be as
     * up-to-date as possible.
     */
    fun getKioskItems(): List<KioskItem>

    /**
     * Add an item to the cart, presumably this would also be a server call, to allow for the cart
     * to persist between devices. (The importance of this persistence would be pretty dependant on
     * the overall needs, and would probably be a product call.)
     */
    fun changeItemAmountInCart(item: KioskItem, amount: Int)

    /**
     * Get the list of items currently in the user's cart
     */
    fun getCartItems()
}

class KioskRepoImpl @Inject constructor() : KioskRepo {
    override fun getKioskItems(): List<KioskItem> {
        return KioskItemList
    }

    override fun changeItemAmountInCart(item: KioskItem, amount: Int) {
        val newAmount = cartHashMap.getOrDefault(item, 0) + amount
        if (newAmount != 0) {
            cartHashMap[item] = newAmount
        } else {
            cartHashMap.remove(item)
        }
    }

    override fun getCartItems() {

        cartHashMap.keys.forEach { key ->
            Log.i("MARKED Ω", "Cart Item: $key x ${cartHashMap[key]}")
        }
    }

    companion object{
        val cartHashMap: HashMap<KioskItem, Int> = HashMap<KioskItem, Int>()
    }
}


private val KioskItemList = listOf(
    KioskItem(
        "Item Zero",
        "An item defined by it's existence at 0', 0' on the map. Luckily it's waterproof.",
        "Zero",
        150.0,
        0.0,
        0.0
    ),
    KioskItem(
        "Item One",
        "Strictly speaking, it's the second item. That's indices, baby!",
        "One",
        50.0,
        30.0,
        30.0
    ),
    KioskItem(
        "Item Two",
        "It's rather negative, but try to be understanding",
        "Two",
        10.0,
        -15.0,
        -15.0
    ),
    KioskItem(
        "Custom Fit Item",
        "An item that could be closer. Especially if you edit the code. This is true of every item, really.",
        "Custom",
        75.0,
        37.68832564911537,
        -122.43693702466099
    ),
    KioskItem(
        "Our Lawyer Made Us Change The Name Of This Item So We Wouldn't Get Sued",
        "It was actually changed for the sake of testing. But we can't say if lawyers were involved or not.",
        "Corktree",
        150.0,
        37.75426961946803,
        -122.45237250153855
    ),
    KioskItem(
        "THE Tony Packo Hot Dog",
        "A favorite of corpsmen everywhere.",
        "Packo",
        2.95,
        41.65997804188749,
        -83.50212186996635
    ),
    KioskItem(
        "Ukelele",
        "A musical device that's like a guitar, but quirkier while still sounding nice.",
        "Ukelele",
        230.0,
        37.791358805376284,
        -122.4848957436539
    ),
    KioskItem(
        "Forklift Pack",
        "Devices you can ride in to life heavy things, provided they're on a pallet. Pack of 4. Serious lifters only",
        "Forklifts",
        49500.0,
        0.0,
        5.0
    ),
    KioskItem(
        "Space Filler",
        "A large object devoid of any other definable qualities. It's big.",
        "Space",
        1.0,
        30.0,
        -115.0
    ),
    KioskItem(
        "A fine duck",
        "One good bird. Aquatically inclined",
        "DuckPlus",
        0.1,
        37.6867883226709,
        -122.3943727561881
    )
)