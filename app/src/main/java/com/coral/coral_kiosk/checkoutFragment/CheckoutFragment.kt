package com.coral.coral_kiosk.checkoutFragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.coral.coral_kiosk.R

class CheckoutFragment : Fragment() {

    companion object {
        fun newInstance() = CheckoutFragment()
    }

    private val viewModel: CheckoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailsButton = view.findViewById<Button>(R.id.checkoutToListButton)
        detailsButton.setOnClickListener { v: View? -> navToList() }
    }

    fun navToList() {
        findNavController().navigate(R.id.action_checkoutFragment_to_listFragment)
    }
}