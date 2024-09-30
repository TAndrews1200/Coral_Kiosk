package com.coral.coral_kiosk.detailsFragment

import com.coral.coral_kiosk.models.KioskItem
import com.coral.coral_kiosk.repos.KioskRepoImpl
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

internal class DetailsViewModelTest {

    lateinit var detailsViewModel: DetailsViewModel
    lateinit var kioskRepoImpl: KioskRepoImpl

    val testItem = KioskItem(
        "TestItem",
        "A test item",
        150.0,
        0.0,
        0.0
    )

    @Before
    fun init() {
        kioskRepoImpl = KioskRepoImpl()
        detailsViewModel = DetailsViewModel(kioskRepoImpl)
    }

    @Test
    fun testDetailsViewModelGetAndSetCurrentItem() {
        detailsViewModel.currentItem = testItem

        assertEquals(testItem.name, detailsViewModel.currentItem.name)
        assertEquals(testItem.description, detailsViewModel.currentItem.description)
        assertEquals(testItem.price, detailsViewModel.currentItem.price)
        assertEquals(testItem.lat, detailsViewModel.currentItem.lat)
        assertEquals(testItem.long, detailsViewModel.currentItem.long)
    }

    @Test
    fun testDetailsViewModelAddToCart() {
        assertEquals(0, kioskRepoImpl.getCartItemsAsList().size) //Ensure list started empty
        detailsViewModel.currentItem = testItem
        detailsViewModel.addToCart(5)
        val newList = kioskRepoImpl.getCartItemsAsList()
        assertEquals(1, newList.size) //Ensure we have an item now
        assertEquals(testItem.name, newList[0].first.name) //Ensure new item is correct item
        assertEquals(5, newList[0].second) //Ensure the correct quantity was added

    }
}