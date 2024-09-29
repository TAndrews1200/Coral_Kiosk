package com.coral.coral_kiosk.cartFragment

import androidx.lifecycle.ViewModel
import com.coral.coral_kiosk.repos.KioskRepo
import com.coral.coral_kiosk.models.KioskItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val kioskRepo: KioskRepo
) : ViewModel() {
    // TODO: Implement the ViewModel

    fun getItemList(): List<KioskItem> {
        return kioskRepo.getKioskItems()
    }

}