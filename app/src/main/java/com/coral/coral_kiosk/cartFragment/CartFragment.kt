package com.coral.coral_kiosk.cartFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coral.coral_kiosk.R
import com.coral.coral_kiosk.baseClasses.BaseFragment
import com.coral.coral_kiosk.listFragment.CartListAdapter
import com.coral.coral_kiosk.models.KioskItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartPriceTotal: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailsButton = view.findViewById<Button>(R.id.cartFragmentToCheckoutButton)
        detailsButton.setOnClickListener { v: View? -> navToCheckout() }
        cartRecyclerView = view.findViewById(R.id.cartFragmentRecyclerView)
        cartPriceTotal = view.findViewById(R.id.cartFragmentTotalPrice)
    }

    override fun onStart() {
        super.onStart()

        viewModel.updateCartList()

        val listObserver = Observer<List<Pair<KioskItem, Int>>>{ list ->
            val adapter = CartListAdapter(list) { pos ->
                viewModel.removeCartItem(list[pos].first)
            }

            cartRecyclerView.setAdapter(adapter)
            cartRecyclerView.setLayoutManager(
                LinearLayoutManager(this@CartFragment.context)
            )
            cartPriceTotal.text = viewModel.getCartTotalPrice().toString()
        }
        viewModel.activeCartList.observe(this, listObserver)
    }

    fun navToCheckout() {
        findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
    }
}