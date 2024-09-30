package com.coral.coral_kiosk.listFragment;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coral.coral_kiosk.R;
import com.coral.coral_kiosk.baseClasses.BaseFragment;
import com.coral.coral_kiosk.models.KioskItem;

import java.lang.ref.WeakReference;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ListFragment extends BaseFragment {

    private ListViewModel mViewModel;
    private RecyclerView kioskRecyclerView;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        kioskRecyclerView = view.findViewById(R.id.listFragmentRecyclerView);
        SwipeRefreshLayout kioskSwipeLayout = view.findViewById(R.id.listFragmentSwipeLayout);

        cartButton.setOnClickListener(v -> navToCart());
        kioskSwipeLayout.setOnRefreshListener(() -> {
            checkPermissionAndUpdateLocation();
            kioskSwipeLayout.setRefreshing(false);
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();

        /*
        While a weak reference can produce a null,
        if this fragment has gone null the adapter should also be gone.
         */
        WeakReference<ListFragment> weakListFragment = new WeakReference<>(this);
        final Observer<Location> locationObserver = new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                Log.i("MARKED Ω", "Changing...");
                KioskListAdapter adapter =
                        new KioskListAdapter(mViewModel.getItemList(),
                                mViewModel.getLastKnownLocation().getValue(),
                                selectedItem -> weakListFragment.get().navToDetails(selectedItem));
                kioskRecyclerView.setAdapter(adapter);
                kioskRecyclerView.setLayoutManager(
                        new LinearLayoutManager(weakListFragment.get().getContext()));
            }
        };
        mViewModel.getLastKnownLocation().observe(this, locationObserver);

        if (mViewModel.getLastKnownLocation().getValue() == null) {
            checkPermissionAndUpdateLocation();
        }
    }

    private void checkPermissionAndUpdateLocation() {
        if (ActivityCompat.checkSelfPermission(ListFragment.this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mViewModel.updateUserLocation((LocationManager) ListFragment.this.requireActivity()
                    .getSystemService(Context.LOCATION_SERVICE));
        }
    }

    public void navToDetails(KioskItem selectedItem) {
        findNavController(this)
                .navigate(ListFragmentDirections.actionListFragmentToDetailsFragment(selectedItem));
    }

    public void navToCart() {
        findNavController(this).navigate(R.id.action_listFragment_to_cartFragment);
    }
}