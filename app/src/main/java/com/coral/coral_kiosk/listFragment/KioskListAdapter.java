package com.coral.coral_kiosk.listFragment;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.coral.coral_kiosk.R;
import com.coral.coral_kiosk.models.KioskItem;
import com.coral.coral_kiosk.utils.LocationTool;

import java.util.List;

/**
 * The Adapter for any recycler view that wants to work with Kiosk Items.
 */
public class KioskListAdapter extends
        RecyclerView.Adapter<KioskListAdapter.ViewHolder> {

    private final List<KioskItem> itemList;
    private final OnItemClickListener itemClickListener;
    private final Location userLocation;

    /**
     * KioskListAdapter constructor
     *
     * @param itemList list of KioskItems to display
     * @param userLocation the location object of the user, for highlights.
     * @param clickListener clickListener for the overall view
     */
    public KioskListAdapter(List<KioskItem> itemList,
                            Location userLocation,
                            OnItemClickListener clickListener) {
        this.itemList = itemList;
        this.userLocation = userLocation;
        this.itemClickListener = clickListener;
    }

    @NonNull
    @Override
    public KioskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.kiosk_list_item, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(KioskListAdapter.ViewHolder holder, int position) {
        KioskItem kioskItem = itemList.get(position);
        holder.nameTextView.setText(kioskItem.getName());
        holder.descTextView.setText(kioskItem.getDescription());

        if (LocationTool.INSTANCE.distanceToItem(userLocation, kioskItem) < 10000f ) {
            holder.rootCardView.setCardBackgroundColor(Color.BLUE);
        } else {
            holder.rootCardView.setCardBackgroundColor(Color.GRAY);
        }

        holder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(itemList.get(position)));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView descTextView;
        public CardView rootCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.kioskListItemName);
            descTextView = itemView.findViewById(R.id.kioskListItemDescription);
            rootCardView = itemView.findViewById(R.id.kioskListItemCardView);
        }
    }
}

/**
 * Simple click listener for getting feedback from list items.
 */
interface OnItemClickListener {
    void onItemClick(KioskItem item);
}