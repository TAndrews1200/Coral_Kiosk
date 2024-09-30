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
import android.widget.TextView;

import com.coral.coral_kiosk.R;
import com.coral.coral_kiosk.baseClasses.BaseFragment;
import com.coral.coral_kiosk.models.KioskItem;

import java.lang.ref.WeakReference;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ListFragment extends BaseFragment {

    private ListViewModel mViewModel;
    private RecyclerView kioskRecyclerView;
    private TextView locationText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button cartButton = view.findViewById(R.id.listFragmentToCartButton);
        kioskRecyclerView = view.findViewById(R.id.listFragmentRecyclerView);
        locationText = view.findViewById(R.id.listFragmentLocationText);
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
    public void onStart() {
        super.onStart();

        /*
        While a weak reference can produce a null,
        if this fragment has gone null the adapter should also be gone.
         */
        WeakReference<ListFragment> weakListFragment = new WeakReference<>(this);
        final Observer<Location> locationObserver = location -> {
            setUpKioskItemList(location, weakListFragment);
        };
        mViewModel.getLastKnownLocation().observe(this, locationObserver);

        if (mViewModel.getLastKnownLocation().getValue() == null) {
            checkPermissionAndUpdateLocation();
        }
    }

    /**
     * Set up the recycler view for the KioskItemList
     *
     * @param userLocation User's Location, used to highlight nearby items
     * @param weakListFragment Weak reference to this fragment
     */
    private void setUpKioskItemList(Location userLocation, WeakReference<ListFragment> weakListFragment) {
        locationText.setText(
                getString(R.string.current_location_s_by_s,
                        String.valueOf(userLocation.getLatitude()),
                        String.valueOf(userLocation.getLongitude())));

        KioskListAdapter adapter =
                new KioskListAdapter(mViewModel.getItemList(),
                        mViewModel.getLastKnownLocation().getValue(),
                        selectedItem -> weakListFragment.get().navToDetails(selectedItem));
        kioskRecyclerView.setAdapter(adapter);
        kioskRecyclerView.setLayoutManager(
                new LinearLayoutManager(weakListFragment.get().getContext()));
    }

    /**
     * Update our location ONLY IF we have the permission to check for location
     */
    private void checkPermissionAndUpdateLocation() {
        if (ActivityCompat.checkSelfPermission(ListFragment.this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mViewModel.updateUserLocation((LocationManager) ListFragment.this.requireActivity()
                    .getSystemService(Context.LOCATION_SERVICE));
        }
    }

    /**
     * Go to the Details screen for a certain item.
     *
     * @param selectedItem the item we want details for.
     */
    public void navToDetails(KioskItem selectedItem) {
        findNavController(this)
                .navigate(ListFragmentDirections.actionListFragmentToDetailsFragment(selectedItem));
    }

    /**
     * Go to the Cart
     */
    public void navToCart() {
        findNavController(this).navigate(R.id.action_listFragment_to_cartFragment);
    }
}