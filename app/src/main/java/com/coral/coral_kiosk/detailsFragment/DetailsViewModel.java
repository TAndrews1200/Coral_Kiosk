package com.coral.coral_kiosk.detailsFragment;

import androidx.lifecycle.ViewModel;

import com.coral.coral_kiosk.models.KioskItem;
import com.coral.coral_kiosk.repos.KioskRepo;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DetailsViewModel extends ViewModel {

    private final KioskRepo kioskRepo;
    private KioskItem currentItem;


    public KioskItem getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(KioskItem currentItem) {
        this.currentItem = currentItem;
    }

    @Inject
    DetailsViewModel(KioskRepo kioskRepo){
        this.kioskRepo = kioskRepo;
    }

    public void addToCart(int quantity){
        kioskRepo.changeItemAmountInCart(getCurrentItem(), quantity);
    }
}