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

    fun updateCartList() {
        activeCartList.value = kioskRepo.getCartItemsAsList()
    }

    fun removeCartItem(item: KioskItem) {
        kioskRepo.removeCartItem(item)
        updateCartList()
    }

    fun getCartTotalPrice(): Double {
        var priceTotal = 0.0
        activeCartList.value.orEmpty().forEach { itemPair ->
            priceTotal += (itemPair.first.price * itemPair.second)
        }
        return priceTotal
    }
}