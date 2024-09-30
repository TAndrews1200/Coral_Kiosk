package com.coral.coral_kiosk.cartFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartPriceTotal: TextView
    private lateinit var checkoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkoutButton = view.findViewById(R.id.cartFragmentCheckoutButton)
        cartRecyclerView = view.findViewById(R.id.cartFragmentRecyclerView)
        cartPriceTotal = view.findViewById(R.id.cartFragmentTotalPrice)
        checkoutButton.setOnClickListener { v: View? -> checkoutPressed() }
    }

    override fun onStart() {
        super.onStart()

        viewModel.updateCartList()

        val listObserver = Observer<List<Pair<KioskItem, Int>>> { list ->
            val adapter = CartListAdapter(list) { pos ->
                viewModel.removeCartItem(list[pos].first)
            }

            cartRecyclerView.setAdapter(adapter)
            cartRecyclerView.setLayoutManager(
                LinearLayoutManager(this@CartFragment.context)
            )
        }
        val priceObserver = Observer<Double> { price ->
            cartPriceTotal.text = "$$price"
        }
        val quantityObserver = Observer<Int> { quantity ->
            checkoutButton.setText("Buy $quantity items!")
        }
        viewModel.activeCartList.observe(this, listObserver)
        viewModel.activeCartPrice.observe(this, priceObserver)
        viewModel.activeCartQuantity.observe(this, quantityObserver)
    }

    fun checkoutPressed() {
        val alertDialogBuilder = AlertDialog.Builder(this.requireContext())
        alertDialogBuilder.setTitle("Checkout?")
        alertDialogBuilder
            .setMessage("Do you want to checkout your ${viewModel.activeCartQuantity.value} items, " +
                    "costing a total of $${viewModel.activeCartPrice.value}?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            Log.i("MARKED Ω", "Checkout!")
            checkoutConfirmed()
        }
        alertDialogBuilder.setNegativeButton("No") { _, _ ->
            //No op, it just closes the dialog.
        }
        alertDialogBuilder.create().show()
    }

    private fun checkoutConfirmed() {
        val checkedOut = viewModel.checkoutCart()
        Toast.makeText(
            requireContext(),
            "Bought $checkedOut items!",
            Toast.LENGTH_SHORT
        ).show()
        findNavController().navigate(CartFragmentDirections.actionCartFragmentToListFragment())
    }
}