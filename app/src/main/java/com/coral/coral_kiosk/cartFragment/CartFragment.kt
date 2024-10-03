package com.coral.coral_kiosk.cartFragment

import android.os.Bundle
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
    private lateinit var cartEmptyListTextView: TextView
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
        cartEmptyListTextView = view.findViewById(R.id.cartFragmentEmptyCartTextView)
        cartPriceTotal = view.findViewById(R.id.cartFragmentTotalPrice)
        checkoutButton.setOnClickListener { v: View? -> displayCheckoutDialog() }
    }

    override fun onStart() {
        super.onStart()

        viewModel.updateCartStats()

        val listObserver = Observer<List<Pair<KioskItem, Int>>> { list ->
            setupCartList(list)
        }
        val priceObserver = Observer<Double> { price ->
            cartPriceTotal.text = getString(R.string.generic_dollar_sign_format, price.toString())
        }
        val quantityObserver = Observer<Int> { quantity ->
            val hasItems = quantity > 0
            cartRecyclerView.visibility = if (hasItems) View.VISIBLE else View.INVISIBLE
            cartEmptyListTextView.visibility = if (hasItems) View.INVISIBLE else View.VISIBLE
            checkoutButton.isEnabled = hasItems

            checkoutButton.setText(
                if (hasItems) getString(R.string.buy_x_items, quantity)
                else getString(R.string.no_items_in_cart)
            )
        }
        viewModel.activeCartList.observe(this, listObserver)
        viewModel.activeCartPrice.observe(this, priceObserver)
        viewModel.activeCartQuantity.observe(this, quantityObserver)
    }

    /**
     * Setup the item list for the cart
     *
     * @list List of Pairs, the first parameter being the KioskItem carted,
     *       the second parameter being the quantity carted
     */
    private fun setupCartList(list: List<Pair<KioskItem, Int>>) {
        val adapter = CartListAdapter(list) { pos ->
            viewModel.removeCartItem(list[pos].first)
        }

        cartRecyclerView.setAdapter(adapter)
        cartRecyclerView.setLayoutManager(
            LinearLayoutManager(this@CartFragment.context)
        )
    }

    /**
     * Show the Checkout Dialog
     */
    private fun displayCheckoutDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this.requireContext())
        alertDialogBuilder.setTitle(getString(R.string.question_checkout))
        alertDialogBuilder
            .setMessage(
                getString(
                    R.string.double_check_checkout,
                    viewModel.activeCartQuantity.value.toString(),
                    viewModel.activeCartPrice.value.toString()
                )
            )
        alertDialogBuilder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            checkoutConfirmed()
        }
        alertDialogBuilder.setNegativeButton(getString(R.string.no)) { _, _ ->
            //No op, it just closes the dialog.
        }
        alertDialogBuilder.create().show()
    }

    /**
     * Activate Checkout, display related Toast, return to List to buy more.
     */
    private fun checkoutConfirmed() {
        val checkedOut = viewModel.checkoutCart()
        Toast.makeText(
            requireContext(),
            getString(R.string.bought_x_items, checkedOut),
            Toast.LENGTH_SHORT
        ).show()
        findNavController().navigate(CartFragmentDirections.actionCartFragmentToListFragment())
    }
}