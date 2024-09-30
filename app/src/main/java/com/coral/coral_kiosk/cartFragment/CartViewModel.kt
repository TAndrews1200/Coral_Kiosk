package com.coral.coral_kiosk.cartFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coral.coral_kiosk.models.KioskItem
import com.coral.coral_kiosk.repos.KioskRepo
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun updateCartList() {
        activeCartList.value = kioskRepo.getCartItemsAsList()
        updateCartTotals()
    }

    fun removeCartItem(item: KioskItem) {
        kioskRepo.removeCartItem(item)
        updateCartList()
    }

    /**
     * Returns the total Price and Quantity
     *
     * @return Pair<Double, Int> where first is price and second is quantity
     */
    fun updateCartTotals() {
        var quantityTotal = 0
        var priceTotal = 0.0
        activeCartList.value.orEmpty().forEach { itemPair ->
            quantityTotal += itemPair.second
            priceTotal += (itemPair.first.price * itemPair.second)
        }
        activeCartPrice.value = priceTotal
        activeCartQuantity.value = quantityTotal
    }

    fun checkoutCart(): Int {
        return kioskRepo.checkoutCart()
    }
}