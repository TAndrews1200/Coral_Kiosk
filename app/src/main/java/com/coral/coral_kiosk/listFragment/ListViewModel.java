package com.coral.coral_kiosk.listFragment;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.coral.coral_kiosk.repos.KioskRepo;
import com.coral.coral_kiosk.models.KioskItem;

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

    public List<KioskItem> getItemList(){
        return kioskRepo.getKioskItems();
    }

}