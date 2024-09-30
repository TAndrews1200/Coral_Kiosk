package com.coral.coral_kiosk.cartFragment

import androidx.test.annotation.UiThreadTest
import com.coral.coral_kiosk.models.KioskItem
import com.coral.coral_kiosk.repos.KioskRepoImpl
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
internal class CartViewModelTest {

    @get:Rule
    var hiltAndroidRule: HiltAndroidRule = HiltAndroidRule(this)

    lateinit var cartViewModel: CartViewModel
    lateinit var kioskRepoImpl: KioskRepoImpl

    val testItem1Quantity = 1
    val testItem2Quantity = 2
    val testItem1 = KioskItem(
        "TestItem",
        "A first test item",
        100.0,
        0.0,
        0.0
    )
    val testItem2 = KioskItem(
        "TestItem2",
        "A second test item",
        10.0,
        0.0,
        0.0
    )

    @Before
    fun init() {
        kioskRepoImpl = KioskRepoImpl()
        kioskRepoImpl.checkoutCart()
        cartViewModel = CartViewModel(kioskRepoImpl)
    }

    @After
    fun tearDown() {
        kioskRepoImpl.checkoutCart()
    }

    @Test
    fun testCartViewModelListAlterationsInSequence() {
        kioskRepoImpl.changeItemAmountInCart(testItem1, testItem1Quantity)
        kioskRepoImpl.changeItemAmountInCart(testItem2, testItem2Quantity)
        //These will give the postValues a chance to work themselves out. Normally not a problem,
        //But occasionally the test runner reads a little too fast
        Thread.sleep(50)

        cartViewModel.updateCartStats()

        Thread.sleep(50)
        assertEquals(testItem1.name, cartViewModel.activeCartList.value?.get(0)?.first?.name)
        assertEquals(testItem1Quantity, cartViewModel.activeCartList.value?.get(0)?.second)
        assertEquals(testItem2.name, cartViewModel.activeCartList.value?.get(1)?.first?.name)
        assertEquals(testItem2Quantity, cartViewModel.activeCartList.value?.get(1)?.second)

        assertEquals(2, cartViewModel.activeCartList.value?.size )

        cartViewModel.removeCartItem(testItem1)
        Thread.sleep(50)
        assertEquals(1, cartViewModel.activeCartList.value?.size )

        val checkoutReturnValue = cartViewModel.checkoutCart()

        Thread.sleep(50)
        assertEquals(2, checkoutReturnValue)
        assertEquals(0, cartViewModel.activeCartList.value?.size)
    }
}