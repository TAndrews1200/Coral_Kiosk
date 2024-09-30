package com.coral.coral_kiosk.cartFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.coral.coral_kiosk.R
import com.coral.coral_kiosk.baseClasses.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailsButton = view.findViewById<Button>(R.id.cartToCheckoutButton)
        detailsButton.setOnClickListener { v: View? -> navToCheckout() }
        viewModel.getCartList()
    }

    fun navToCheckout() {
        findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
    }
}