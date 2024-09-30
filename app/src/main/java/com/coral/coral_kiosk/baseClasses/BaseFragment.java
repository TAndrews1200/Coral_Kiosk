package com.coral.coral_kiosk.baseClasses;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.FragmentKt;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavController navController = FragmentKt.findNavController(this);
        OnBackPressedCallback callback =

                //Process back presses through the nav graph before the overall activity
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        navController.popBackStack();
                    }
                };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
