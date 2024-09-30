package com.coral.coral_kiosk.listFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coral.coral_kiosk.R
import com.coral.coral_kiosk.models.KioskItem

/**
 * The Adapter for the cart interface, to changing the inventory of the cart.
 *
 * @param itemList list of KioskItems to display
 * @param userLocation the location object of the user, for highlights.
 */
class CartListAdapter(
    private val itemList: List<Pair<KioskItem, Int>>,
    private val onRemoveClick: (Int) -> Unit
) : RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.cart_list_item, parent, false)

        val viewHolder = ViewHolder(contactView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kioskItem = itemList[position].first
        val itemQuantity = itemList[position].second

        holder.nameTextView.text = kioskItem.name
        holder.quantityTextView.text = "x$itemQuantity"
        holder.priceTextView.text = "$${itemQuantity * kioskItem.price}"

        holder.itemRemoveButton.setOnClickListener {
            onRemoveClick(position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.cartListItemName)
        var quantityTextView: TextView = itemView.findViewById(R.id.cartListItemQuantity)
        var priceTextView: TextView = itemView.findViewById(R.id.cartListItemPriceTotal)
        var itemRemoveButton: Button = itemView.findViewById(R.id.cartListItemRemove)
    }
}