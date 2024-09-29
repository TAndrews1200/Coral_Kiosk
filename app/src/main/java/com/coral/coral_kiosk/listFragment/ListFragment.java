package com.coral.coral_kiosk.listFragment;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.coral.coral_kiosk.R;
import com.coral.coral_kiosk.models.KioskItem;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ListFragment extends Fragment {

    private ListViewModel mViewModel;
    private RecyclerView kioskRecyclerView;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListViewModel.class);
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
        kioskRecyclerView = view.findViewById(R.id.listFragmentRecyclerView);

        cartButton.setOnClickListener(v -> navToCart());
        detailsButton.setOnClickListener(v -> navToDetails());

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        KioskListAdapter adapter = new KioskListAdapter(mViewModel.getItemList());
        kioskRecyclerView.setAdapter(adapter);
        kioskRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        List<KioskItem> internalList = mViewModel.getItemList();

        for (int x = 0; x < internalList.size(); x++) {
            Log.i("MARKED Ω", "List Item " + x + ": " + internalList.get(x));
        }
    }

    public void navToDetails() {
        findNavController(this).navigate(R.id.action_listFragment_to_detailsFragment);
    }

    public void navToCart() {
        findNavController(this).navigate(R.id.action_listFragment_to_cartFragment);
    }

}