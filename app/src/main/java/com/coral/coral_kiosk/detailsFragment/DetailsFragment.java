package com.coral.coral_kiosk.detailsFragment;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coral.coral_kiosk.R;
import com.coral.coral_kiosk.models.KioskItem;

public class DetailsFragment extends Fragment {

    private DetailsViewModel mViewModel;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button detailsButton = view.findViewById(R.id.detailsToCartButton);
        TextView itemName = view.findViewById(R.id.detailsFragmentItemName);
        TextView itemDescription = view.findViewById(R.id.detailsFragmentItemDescription);

        KioskItem item = DetailsFragmentArgs.fromBundle(getArguments()).getSelectedItem();

        itemName.setText(item.getName());
        itemDescription.setText(item.getDescription());
        detailsButton.setOnClickListener(v -> navToCart());
    }

    public void navToCart() {
        findNavController(this).navigate(R.id.action_detailsFragment_to_cartFragment);
    }
}