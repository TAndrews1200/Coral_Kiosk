package com.coral.coral_kiosk.listFragment;

import android.Manifest;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.RequiresPermission;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.coral.coral_kiosk.repos.KioskRepo;
import com.coral.coral_kiosk.models.KioskItem;
import com.coral.coral_kiosk.utils.LocationTool;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ListViewModel extends ViewModel {

    private final KioskRepo kioskRepo;

    @Inject
    ListViewModel(KioskRepo kioskRepo){
        this.kioskRepo = kioskRepo;
    }

    private MutableLiveData<Location> lastKnownLocation;

    public MutableLiveData<Location> getLastKnownLocation() {
        if (lastKnownLocation == null) {
            lastKnownLocation = new MutableLiveData<>();
        }
        return lastKnownLocation;
    }

    public List<KioskItem> getItemList(){
        return kioskRepo.getKioskItems();
    }

    @RequiresPermission(allOf = Manifest.permission.ACCESS_FINE_LOCATION)
    public void updateUserLocation(LocationManager locationManager){
        Log.i("MARKED Ω","Updating...");
        lastKnownLocation.setValue(LocationTool.INSTANCE.getLocation(locationManager));
    }

}