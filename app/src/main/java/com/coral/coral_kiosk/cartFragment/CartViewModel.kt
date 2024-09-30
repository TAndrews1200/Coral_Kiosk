package com.coral.coral_kiosk.cartFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coral.coral_kiosk.models.KioskItem
import com.coral.coral_kiosk.repos.KioskRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.BigDecimal
import java.math.MathContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val kioskRepo: KioskRepo
) : ViewModel() {

    val activeCartList: MutableLiveData<List<Pair<KioskItem, Int>>> by lazy{
        MutableLiveData<List<Pair<KioskItem, Int>>>(kioskRepo.getCartItemsAsList())
    }
    val activeCartPrice: MutableLiveData<Double> by lazy{ MutableLiveData<Double>() }
    val activeCartQuantity: MutableLiveData<Int> by lazy{ MutableLiveData<Int>() }

    /**
     * Get the updated Cart List from the kioskRepo
     */
    fun updateCartStats() {
        val cartList = kioskRepo.getCartItemsAsList()
        activeCartList.postValue(kioskRepo.getCartItemsAsList())

        var quantityTotal = 0
        var priceTotal = 0.0
        cartList.forEach { itemPair ->
            quantityTotal += itemPair.second
            priceTotal += BigDecimal(itemPair.first.price * itemPair.second).round(MathContext(3)).toDouble()
        }

        activeCartPrice.postValue(priceTotal)
        activeCartQuantity.postValue(quantityTotal)
    }

    /**
     * Wholly remove an item from the cart.
     */
    fun removeCartItem(item: KioskItem) {
        kioskRepo.removeCartItem(item)
        updateCartStats()
    }

    /**
     * Checkout Cart, clearing everything from the map (This is very much a simplification)
     */
    fun checkoutCart(): Int {
        val checkedOut = kioskRepo.checkoutCart()
        updateCartStats()
        return checkedOut
    }
}