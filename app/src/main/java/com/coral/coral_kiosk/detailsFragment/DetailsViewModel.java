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

    @Inject
    DetailsViewModel(KioskRepo kioskRepo) {
        this.kioskRepo = kioskRepo;
    }

    /**
     * Access the current KioskItem we want the details for
     *
     * @return the current KioskItem
     */
    public KioskItem getCurrentItem() {
        return currentItem;
    }

    /**
     * Tell the ViewModel what item we specifically care about
     *
     * @param currentItem the item we are currently focusing on
     */
    public void setCurrentItem(KioskItem currentItem) {
        this.currentItem = currentItem;
    }

    /**
     * Add the current item to the cart in a specified quantity
     *
     * @param quantity amount of the the current item to add to cart
     */
    public void addToCart(int quantity){
        kioskRepo.changeItemAmountInCart(getCurrentItem(), quantity);
    }
}