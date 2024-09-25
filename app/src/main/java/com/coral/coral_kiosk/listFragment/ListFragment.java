package com.coral.coral_kiosk.listFragment;

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

import com.coral.coral_kiosk.R;

public class ListFragment extends Fragment {

    private ListViewModel mViewModel;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button cartButton = view.findViewById(R.id.listToCartButton);
        Button detailsButton = view.findViewById(R.id.listToDetailsButton);

        cartButton.setOnClickListener(v -> navToCart());
        detailsButton.setOnClickListener(v -> navToDetails());
    }

    public void navToDetails() {
        findNavController(this).navigate(R.id.action_listFragment_to_detailsFragment);
    }

    public void navToCart() {
        findNavController(this).navigate(R.id.action_listFragment_to_cartFragment);
    }

}