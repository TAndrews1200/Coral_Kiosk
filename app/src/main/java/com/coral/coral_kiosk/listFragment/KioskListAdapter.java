package com.coral.coral_kiosk.listFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coral.coral_kiosk.R;
import com.coral.coral_kiosk.models.KioskItem;

import java.util.List;

/**
 * The Adapter for any recycler view that wants to work with Kiosk Items.
 */
public class KioskListAdapter extends
        RecyclerView.Adapter<KioskListAdapter.ViewHolder> {

    private final List<KioskItem> itemList;

    /**
     * KioskListAdapter constructor
     *
     * @param itemList list of KioskItems to display
     */
    public KioskListAdapter(List<KioskItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public KioskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.kiosk_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(KioskListAdapter.ViewHolder holder, int position) {
        KioskItem kioskItem = itemList.get(position);
        holder.nameTextView.setText(kioskItem.getName());
        holder.descTextView.setText(kioskItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView descTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.kioskListItemName);
            descTextView = itemView.findViewById(R.id.kioskListItemDescription);
        }
    }
}